package com.esoa.demo.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.esoa.demo.entity.Animal;
import com.esoa.demo.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;


    @Transactional
    public void create(Animal dto, MultipartFile photo){
        if (animalRepository.existsByNameAndDescription(dto.getName(), dto.getDescription()))
            throw new IllegalArgumentException("Error!");
        
        Animal animal = new Animal();
        animal.setName(dto.getName());
        animal.setDescription(dto.getDescription());
        animal.setDischargeDate(LocalDate.now());
        animal.setCategory(dto.getCategory());
        animal.setScientificName(dto.getScientificName());
        animal.setSpecie(dto.getSpecie());
        animal.setDeleted(false);
        if (!photo.isEmpty()){
            Path directoryImage = Paths.get("demo//src//main//resources//static//uploads/animals");
            String rutaAbsoluta = directoryImage.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = photo.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ photo.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                animal.setImage(photo.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        animalRepository.save(animal);

    }

    @Transactional
    public void update(Animal dto, MultipartFile photo){
        
        Animal animal = animalRepository.findById(dto.getId()).get();
        animal.setName(dto.getName());
        animal.setDescription(dto.getDescription());
        animal.setDischargeDate(dto.getDischargeDate());
        animal.setCategory(dto.getCategory());
        animal.setScientificName(dto.getScientificName());
        animal.setSpecie(dto.getSpecie());
        animal.setDeleted(dto.isDeleted());
        if (!photo.isEmpty()){
            Path directoryImage = Paths.get("demo//src//main//resources//static//uploads/animals");
            String rutaAbsoluta = directoryImage.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = photo.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ photo.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                animal.setImage(photo.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        animalRepository.save(animal);

    }

    @Transactional(readOnly = true)
    public Animal getById(Integer id){
        return animalRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Animal> getAll(){
        return animalRepository.findAll();
    }

   
    @Transactional
    public void enableById(Integer id){
        animalRepository.enableById(id);  
    }

    @Transactional
    public void deleteById(Integer id) {
        animalRepository.deleteById(id);
    }

}
