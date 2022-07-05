package com.esoa.demo.service;

import com.esoa.demo.entity.*;
import com.esoa.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void create(Post dto) {

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setIntroduction(dto.getIntroduction());
        post.setDischargeDate(LocalDate.now());
//if
        post.setAnimal(dto.getAnimal());
//if
        post.setPark(dto.getPark());
//if
        post.setValoration(new ArrayList<Valoration>());

        postRepository.save(post);
    }

    @Transactional
    public void update(Post dto) {

        Post post = postRepository.findById(dto.getId()).get();
        post.setTitle(dto.getTitle());
        post.setIntroduction(dto.getIntroduction());
        post.setDischargeDate(post.getDischargeDate());
        post.setAnimal(dto.getAnimal());
        post.setPark(dto.getPark());

        postRepository.save(post);
    }

    @Transactional
    public void addValoration(Post dto, Valoration valorationDto){
        Post post = postRepository.findById(dto.getId()).get();
        List<Valoration> list = post.getValoration();
        list.add(valorationDto);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post getById(Integer id) {
        return postRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Post getByAnimal(Animal animal) { return postRepository.findById(animal.getId()).get(); }

    @Transactional(readOnly = true)
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Transactional
    public void enableById(Integer id) {
        postRepository.enableById(id);
    }

    @Transactional
    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public List<Post> findAllDeleted(){ return postRepository.findAllDeleted(); }

    @Transactional
    public List<Post> findAllNotDeleted(){ return postRepository.findAllNotDeleted(); }

}
