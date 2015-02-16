package com.feisystems.bham.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.Asi;
import com.feisystems.bham.domain.AsiRepository;
import com.feisystems.bham.domain.AuditRecommendations;
import com.feisystems.bham.domain.Gpra;
import com.feisystems.bham.domain.GpraRepository;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.domain.asi.AsiVariableContainer;
import com.feisystems.bham.domain.gpra.GpraVariableContainer;
import com.feisystems.bham.exceptions.ClinicalDataNotFoundException;
import com.feisystems.bham.exceptions.DataNotFoundException;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;

@Service
@Transactional(rollbackFor = { ClinicalDataNotFoundException.class })
public class DataElementsServiceImpl implements DataElementsService {

	@Autowired
	private GpraRepository gpraRepository;

	@Autowired
	private AsiRepository asiRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DtoToDomainEntityMapper<GpraDto, Gpra> gpraDtoToGpraMapper;

	@Autowired
	private DtoToDomainEntityMapper<AsiDto, Asi> asiDtoToAsiMapper;

	@Autowired
	private DtoToDomainEntityMapper<GpraVariableContainer, Gpra> gpraVariableContainerToGpraMapper;

	@Autowired
	private DtoToDomainEntityMapper<AsiVariableContainer, Asi> asiVariableContainerToAsiMapper;

	@Autowired
	private IndividualProviderRepository individualProviderRepository;

	@Override
	public void saveGpraVariableContainer(GpraVariableContainer container, String requestId, String staffId, String uniqueClientNumber,
			Set<AuditRecommendations> auditRecommendations) {

		Gpra gpra = gpraVariableContainerToGpraMapper.map(container);

		gpra.setRequestId(requestId);
		gpra.setStaffId(staffId);
		gpra.setUniqueClientNumber(uniqueClientNumber);
		gpra.setAuditRecommendations(auditRecommendations);

		gpraRepository.save(gpra);

	}

	@Override
	public GpraDto findGpraDto(String requestId, String staffId) {
		PageRequest pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "timeStamp");
		List<Gpra> dataElementsList = gpraRepository.findByRequestIdAndStaffId(requestId, staffId, pageRequest).getContent();

		if (dataElementsList != null && !dataElementsList.isEmpty()) {
			return modelMapper.map(dataElementsList.get(0), GpraDto.class);
		}
		return null;
	}

	@Override
	public AsiDto findAsiDto(String requestId, String staffId) {
		PageRequest pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "timeStamp");
		List<Asi> asiList = asiRepository.findByRequestIdAndStaffId(requestId, staffId, pageRequest).getContent();

		if (asiList != null && !asiList.isEmpty()) {
			return modelMapper.map(asiList.get(0), AsiDto.class);
		}
		throw new DataNotFoundException(requestId);
	}

	@Override
	public GpraFlagDto findGpraFlagDto(String requestId, String staffId) throws Exception {
		PageRequest pageRequest = new PageRequest(0, 30, Sort.Direction.DESC, "timeStamp");
		List<Gpra> gpraList = gpraRepository.findByRequestIdAndStaffId(requestId, staffId, pageRequest).getContent();

		if (gpraList != null && !gpraList.isEmpty()) {

			List<GpraDto> dtoList = new ArrayList<>();

			for (int i = 0; i < gpraList.size(); i++) {
				dtoList.add(modelMapper.map(gpraList.get(i), GpraDto.class));
			}

			return setFlagsInGpraFlagDto(dtoList.get(0), dtoList.get(dtoList.size() - 1));
		}
		return null;
	}

	@Override
	public AsiFlagDto findAsiFlagDto(String requestId, String staffId) throws Exception {
		PageRequest pageRequest = new PageRequest(0, 30, Sort.Direction.DESC, "timeStamp");
		List<Asi> asiList = asiRepository.findByRequestIdAndStaffId(requestId, staffId, pageRequest).getContent();
		if (asiList != null && !asiList.isEmpty()) {
			List<AsiDto> dtoList = new ArrayList<>();

			for (int i = 0; i < asiList.size(); i++) {
				dtoList.add(modelMapper.map(asiList.get(i), AsiDto.class));
			}

			return setFlagsInAsiFlagDto(dtoList.get(0), dtoList.get(dtoList.size() - 1));
		}
		throw new DataNotFoundException(requestId);
	}

	private GpraFlagDto setFlagsInGpraFlagDto(GpraDto lastDto, GpraDto secondLastDto) throws Exception {
		GpraFlagDto flagDto = modelMapper.map(lastDto, GpraFlagDto.class);
		Field[] fields = lastDto.getClass().getDeclaredFields();

		for (Field f : fields) {
			f.setAccessible(true);

			Object value1 = f.get(lastDto);
			Object value2 = f.get(secondLastDto);

			if (value1 != null && !value1.equals(value2) && !checkInValidField(f)) {
				Field flag = flagDto.getClass().getDeclaredField(f.getName() + "Flag");
				flag.setAccessible(true);

				if (flag != null) {
					flag.set(flagDto, true);
				}
			}
		}

		return flagDto;
	}

	private AsiFlagDto setFlagsInAsiFlagDto(AsiDto lastDto, AsiDto secondLastDto) throws Exception {
		AsiFlagDto flagDto = modelMapper.map(lastDto, AsiFlagDto.class);
		Field[] fields = lastDto.getClass().getDeclaredFields();

		for (Field f : fields) {
			f.setAccessible(true);

			Object value1 = f.get(lastDto);
			Object value2 = f.get(secondLastDto);

			if (value1 != null && !value1.equals(value2) && !checkInValidField(f)) {
				Field flag = flagDto.getClass().getDeclaredField(f.getName() + "Flag");
				flag.setAccessible(true);

				if (flag != null) {
					flag.set(flagDto, true);
				}
			}
		}
		
		return flagDto;
	}

	@Override
	public void saveGpra(GpraDto dto) {

		gpraRepository.save(gpraDtoToGpraMapper.map(dto));

	}

	@Override
	public void saveAsi(AsiDto dto) {

		asiRepository.save(asiDtoToAsiMapper.map(dto));

	}

	@Override
	public void saveAsiVariableContainer(AsiVariableContainer container, String requestId, String staffId, String uniqueClientNumber,
			Set<AuditRecommendations> auditRecommendations) {

		Asi asi = asiVariableContainerToAsiMapper.map(container);
		asi.setRequestId(requestId);
		asi.setStaffId(staffId);
		asi.setUniqueClientNumber(uniqueClientNumber);
		asi.setAuditRecommendations(auditRecommendations);

		asiRepository.save(asi);
	}

	private boolean checkInValidField(Field field) {
		return field.getName().equals("id")
				|| field.getName().equals("staffId")
				|| field.getName().equals("requestId")
				|| field.getName().equals("timeStamp")
				|| field.getName().equals("asi")
				|| field.getName().equals("gpra")
				|| field.getName().contains("DisplayName")
				|| field.getName().equals("recommendationXml")
				|| field.getName().equals("note1")
				|| field.getName().equals("note2");
	}

}
