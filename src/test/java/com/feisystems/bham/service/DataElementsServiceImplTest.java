package com.feisystems.bham.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.feisystems.bham.domain.Asi;
import com.feisystems.bham.domain.AsiRepository;
import com.feisystems.bham.domain.Gpra;
import com.feisystems.bham.domain.GpraRepository;
import com.feisystems.bham.domain.asi.AsiVariableContainer;
import com.feisystems.bham.domain.gpra.GpraVariableContainer;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;


@RunWith(MockitoJUnitRunner.class)
public class DataElementsServiceImplTest {
	
	@InjectMocks
	private DataElementsService service = new DataElementsServiceImpl();
	
	@Mock
	private AsiRepository asiRepo;
	
	@Mock
	private GpraRepository gpraRepo;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private DtoToDomainEntityMapper<GpraVariableContainer, Gpra> gpraVariableContainerToGpraMapper;
	
	@Mock
	private DtoToDomainEntityMapper<AsiVariableContainer, Asi> asiVariableContainerToAsiMapper;
	
	@Mock
	private DtoToDomainEntityMapper<GpraDto, Gpra> gpraDtoToGpraMapper;
	
	@Mock
	private DtoToDomainEntityMapper<AsiDto, Asi> asiDtoToAsiMapper;
	
	@Test
	public void testFindGpraDto() {
		//Arrange
		final Gpra gpra = mock(Gpra.class);
		final Page<Gpra> pageList = new PageImpl<>(Arrays.asList(gpra));
		
		when(gpraRepo.findByRequestIdAndStaffId("requestId", "staffId", getPageRequest(1))).thenReturn(pageList);
		
		final GpraDto gpraDto = mock(GpraDto.class);
		when(modelMapper.map(gpra, GpraDto.class)).thenReturn(gpraDto);
		
		//Act
		GpraDto gpraByRequestId = service.findGpraDto("requestId", "staffId");
		
		//Assert
		assertEquals(gpraDto.getId(), gpraByRequestId.getId());
	}
	
	@Test
	public void testFindAsiDto() {
		//Arrange
		final Asi asi = mock(Asi.class);
		final Page<Asi> pageList = new PageImpl<>(Arrays.asList(asi));
		
		when(asiRepo.findByRequestIdAndStaffId("requestId", "staffId", getPageRequest(1))).thenReturn(pageList);
		
		final AsiDto asiDto = mock(AsiDto.class);
		when(modelMapper.map(asi, AsiDto.class)).thenReturn(asiDto);
		
		//Act
		AsiDto asiByRequestId = service.findAsiDto("requestId", "staffId");
		
		//Assert
		assertEquals(asiDto.getId(), asiByRequestId.getId());
	}
	
	@Test
	public void testSaveGpra() {
		//Arrange
		final GpraDto gpraDto = mock(GpraDto.class);
		final Gpra gpra = mock(Gpra.class);
		when(gpraDtoToGpraMapper.map(gpraDto)).thenReturn(gpra);
		
		//Act
		service.saveGpra(gpraDto);
		
		//Assert
		verify(gpraRepo, times(1)).save(gpra);
	}
	
	@Test
	public void testSaveGpraVariableContainer() {
		// Arrange
		final GpraVariableContainer container = new GpraVariableContainer();
		final Gpra gpra = mock(Gpra.class);
		when(gpraVariableContainerToGpraMapper.map(container)).thenReturn(gpra);
		
		//Act
		service.saveGpraVariableContainer(container, "requestId", "staffId", null);
		
		//Assert
		verify(gpraRepo, times(1)).save(gpra);
	}
	
	@Test
	public void testSaveAsi() {
		//Arrange
		final AsiDto asiDto = mock(AsiDto.class);
		final Asi asi = mock(Asi.class);
		when(asiDtoToAsiMapper.map(asiDto)).thenReturn(asi);
		
		//Act
		service.saveAsi(asiDto);
		
		//Assert
		verify(asiRepo, times(1)).save(asi);
	}
	
	@Test
	public void testSaveAsiVariableContainer() {
		//Arrange
		final AsiVariableContainer container = new AsiVariableContainer();
		final Asi asi = mock(Asi.class);
		when(asiVariableContainerToAsiMapper.map(container)).thenReturn(asi);
		
		//Act
		service.saveAsiVariableContainer(container, "requestId", "staffId", null);
		
		//Assert
		verify(asiRepo, times(1)).save(asi);
	}
	
	private PageRequest getPageRequest(int pages) {
		return new PageRequest(0, pages, Sort.Direction.DESC, "timeStamp");
	}
	

}
