package com.esoa.demo.service;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.esoa.demo.entity.Park;
import com.esoa.demo.repository.ParkRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkService {

    private final ParkRepository parkRepository;

    @Transactional
    public void create(Park dto, MultipartFile photo){
        if (parkRepository.existsByNameAndDescription(dto.getName(), dto.getDescription()))
            throw new IllegalArgumentException("Error!");
        
        Park park = new Park();
        park.setName(dto.getName());
        park.setLocation(dto.getLocation());
        park.setPosition(dto.getPosition());
        park.setDescription(dto.getDescription());
        park.setLink(dto.getLink());
        park.setDischargeDate(LocalDate.now());
        park.setDeleted(false);
        if (!photo.isEmpty()){
            Path directoryImage = Paths.get("demo//src//main//resources//static//uploads/parks");
            String rutaAbsoluta = directoryImage.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = photo.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ photo.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                park.setImage(photo.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        parkRepository.save(park);

        
    }

    @Transactional
    public void update(Park dto, MultipartFile photo){
        
        Park park = parkRepository.findById(dto.getId()).get();
        park.setName(dto.getName());
        park.setLocation(dto.getLocation());
        park.setPosition(dto.getPosition());
        park.setDescription(dto.getDescription());
        park.setLink(dto.getLink());
        park.setDischargeDate(dto.getDischargeDate());
        park.setDeleted(dto.isDeleted());
        if (!photo.isEmpty()){
            Path directoryImage = Paths.get("demo//src//main//resources//static//uploads/parks");
            String rutaAbsoluta = directoryImage.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = photo.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ photo.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                park.setImage(photo.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        parkRepository.save(park);

    }

    @Transactional(readOnly = true)
    public Park getById(Integer id){
        return parkRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Park> getAll(){
        return parkRepository.findAll();
    }

   
    @Transactional
    public void enableById(Integer id){
        parkRepository.enableById(id);   
    }

    @Transactional
    public void deleteById(Integer id) {
        parkRepository.deleteById(id);
    }

}
