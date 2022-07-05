package com.esoa.demo.service;

import com.esoa.demo.entity.User;
import com.esoa.demo.entity.Valoration;
import com.esoa.demo.repository.ValorationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValorationService {

    private final ValorationRepository valorationRepository;

    @Transactional
    public void create(Valoration dto) {

        Valoration valoration = new Valoration();

        valoration.setUser(dto.getUser());
        valoration.setScore(dto.getScore());
        valoration.setValorationDate(dto.getValorationDate());

        valorationRepository.save(valoration);
    }

    @Transactional
    public void update(Valoration dto) {

        Valoration valoration = valorationRepository.findById(dto.getId()).get();

        valoration.setUser(dto.getUser());
        valoration.setScore(dto.getScore());
        valoration.setValorationDate(dto.getValorationDate());

        valorationRepository.save(valoration);
    }

    @Transactional(readOnly = true)
    public Valoration getById(Integer id) {
        return valorationRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Valoration getByUser(User user) {
        return valorationRepository.findById(user.getId()).get();
    }

    @Transactional(readOnly = true)
    public List<Valoration> getAll() {
        return valorationRepository.findAll();
    }


}
