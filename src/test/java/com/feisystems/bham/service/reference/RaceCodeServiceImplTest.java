package com.feisystems.bham.service.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.feisystems.bham.domain.reference.RaceCode;
import com.feisystems.bham.domain.reference.RaceCodeRepository;
import com.feisystems.bham.service.LookupDto;
import com.feisystems.bham.service.reference.RaceCodeService;
import com.feisystems.bham.service.reference.RaceCodeServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class RaceCodeServiceImplTest {

	@InjectMocks
	private RaceCodeService sut = new RaceCodeServiceImpl();
	

	@Mock
	RaceCodeRepository raceCodeRepository;
	
	@Mock
	private ModelMapper modelMapper;

	@Test
	public void testFindRaceCodeEntries() {
		// Arrange
		final RaceCode raceCode1 = mock(RaceCode.class);
		final RaceCode raceCode2 = mock(RaceCode.class);
		List<RaceCode> raceCodeList = new ArrayList<RaceCode>();
		
		raceCodeList.add(raceCode1);
		raceCodeList.add(raceCode2);

		int raceCodeCount = raceCodeList.size();
		
		when(raceCodeRepository.findAll())
		.thenReturn(raceCodeList);
		
		final LookupDto expectedLookupDto1 = mock(LookupDto.class);
		when(modelMapper.map(raceCode1, LookupDto.class)).thenReturn(
				expectedLookupDto1);

		final LookupDto expectedLookupDto2 = mock(LookupDto.class);
		when(modelMapper.map(raceCode2, LookupDto.class)).thenReturn(
				expectedLookupDto2);

		// Act
		List<LookupDto> result = sut.findAllRaceCodes();

		// Assert
		assertEquals(raceCodeCount, result.size());
		for (LookupDto lookupDto : result) {
			assertTrue(lookupDto == expectedLookupDto1
					|| lookupDto == expectedLookupDto2);
		}

		assertTrue(result.get(0) != result.get(1));
	}

}
