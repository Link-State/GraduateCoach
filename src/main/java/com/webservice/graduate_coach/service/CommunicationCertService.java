package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.dto.CommunicationCertDTO;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.webservice.graduate_coach.repository.CommunicationCertRepository;
import com.webservice.graduate_coach.entity.CommunicationCertEntity;

@Service
public class CommunicationCertService {

    @Autowired
    private CommunicationCertRepository repo;

    @Transactional
    public boolean addCommunicationCert(CommunicationCertDTO dto) {
        try {
            CommunicationCertEntity e = CommunicationCertEntity.builder()
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
    public boolean deleteCommunicationCerts(List<Integer> ids) {
        try {
            repo.deleteAllByUIDIn(ids);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<CommunicationCertDTO> getCommunicationCerts(int department, int year) {
        return repo.findByDepartmentAndYear(department, year)
                .stream()
                .map(e -> new CommunicationCertDTO(e.getUID(), e.getName(), e.getDescript(),
                        e.getScore(), e.getDepartment(), e.getYear()))
                .toList();
    }
}
