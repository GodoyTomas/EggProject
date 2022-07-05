
package com.esoa.demo.service;

import com.esoa.demo.entity.Specie;
import com.esoa.demo.repository.SpecieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecieService {

    private final SpecieRepository specieRepository;

    @Transactional
    public void create(Specie dto) {
        if (specieRepository.existsByName(dto.getName()) &&
        specieRepository.existsByOrder(dto.getOrder()) &&
        specieRepository.existsByGenus(dto.getGenus()) )
            throw new IllegalArgumentException("Error!");

        Specie specie = new Specie();
        specie.setName(dto.getName());
        specie.setKingdom(dto.getKingdom());
        specie.setPhylum(dto.getPhylum());
        specie.setOrder(dto.getOrder());
        specie.setFamily(dto.getFamily());
        specie.setGenus(dto.getGenus());
        specie.setDeleted(false);
        specieRepository.save(specie);
    }

    @Transactional
    public void update(Specie dto) {
        Specie specie = specieRepository.findById(dto.getId()).get();

        specie.setName(dto.getName());
        specie.setKingdom(dto.getKingdom());
        specie.setPhylum(dto.getPhylum());
        specie.setOrder(dto.getOrder());
        specie.setFamily(dto.getFamily());
        specie.setGenus(dto.getGenus());

        specieRepository.save(specie);
    }

    @Transactional
    public void enableById(Integer id) {
        specieRepository.enableById(id);
    }

    @Transactional
    public void deleteById(Integer id) {
        specieRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Specie getById(Integer id) {
        return specieRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Specie> getAll() {
        return specieRepository.findAll();
    }

    @Transactional
    public List<Specie> getAllSortedByKingdomAsc(){ return specieRepository.findAllSortedByKingdomAsc(); }

    @Transactional
    public List<Specie> getAllSortedByKingdomDesc(){ return specieRepository.findAllSortedByKingdomDesc(); }

    @Transactional
    public List<Specie> findAllDeleted(){ return specieRepository.findAllDeleted(); }

    @Transactional
    public List<Specie> findAllNotDeleted(){ return specieRepository.findAllNotDeleted(); }

}
