package com.feisystems.bham.service.reference;

import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.AdministrativeGenderCodeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdministrativeGenderCodeServiceImpl implements AdministrativeGenderCodeService {

	@Autowired
    AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	public List<AdministrativeGenderCode> findAllAdministrativeGenderCodes() {
        return administrativeGenderCodeRepository.findAll();
    }

}
