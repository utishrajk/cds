package com.feisystems.bham.service.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.feisystems.bham.domain.reference.StateCode;
import com.feisystems.bham.domain.reference.StateCodeRepository;
import com.feisystems.bham.service.LookupDto;
import com.feisystems.bham.service.reference.StateCodeService;
import com.feisystems.bham.service.reference.StateCodeServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class StateCodeServiceImplTest {

	@InjectMocks
	private StateCodeService sut = new StateCodeServiceImpl();
	
	@Mock
	StateCodeRepository stateCodeRepository;
	
	@Mock
	private ModelMapper modelMapper;

	@Test
	public void testFindStateCodeEntries() {
		// Arrange
		final StateCode stateCode1 = mock(StateCode.class);
		final StateCode stateCode2 = mock(StateCode.class);
		List<StateCode> stateCodeList = new ArrayList<StateCode>();
		
		stateCodeList.add(stateCode1);
		stateCodeList.add(stateCode2);

		int stateCodeCount = stateCodeList.size();
		
		when(stateCodeRepository.findAll())
		.thenReturn(stateCodeList);
		
		final LookupDto expectedLookupDto1 = mock(LookupDto.class);
		when(modelMapper.map(stateCode1, LookupDto.class)).thenReturn(
				expectedLookupDto1);

		final LookupDto expectedLookupDto2 = mock(LookupDto.class);
		when(modelMapper.map(stateCode2, LookupDto.class)).thenReturn(
				expectedLookupDto2);

		// Act
		List<LookupDto> result = sut.findAllStateCodes();

		// Assert
		assertEquals(stateCodeCount, result.size());
		for (LookupDto lookupDto : result) {
			assertTrue(lookupDto == expectedLookupDto1
					|| lookupDto == expectedLookupDto2);
		}

		assertTrue(result.get(0) != result.get(1));
	}

}
