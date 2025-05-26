package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.dto.ForeignCertDTO;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.webservice.graduate_coach.repository.ForeignCertRepository;
import com.webservice.graduate_coach.entity.ForeignCertEntity;

@Service
public class ForeignCertService {

    @Autowired
    private ForeignCertRepository repo;

    @Transactional
    public boolean addForeignCert(ForeignCertDTO dto) {
        try {
            ForeignCertEntity e = ForeignCertEntity.builder()
                    .name(dto.getName())
                    .descript(dto.getDescript())
                    .score(dto.getScore())
                    .department(dto.getDepartment())
                    .year(dto.getYear())
                    .build();
            repo.save(e);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Transactional
    public boolean deleteForeignCerts(List<Integer> ids) {
        try {
            repo.deleteAllByIdIn(ids);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<ForeignCertDTO> getForeignCerts(int department, int year) {
        return repo.findByDepartmentAndYear(department, year)
                .stream()
                .map(e -> new ForeignCertDTO(e.getUID(), e.getName(), e.getDescript(),
                        e.getScore(), e.getDepartment(), e.getYear()))
                .toList();
    }
}
