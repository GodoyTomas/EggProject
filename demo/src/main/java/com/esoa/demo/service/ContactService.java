package com.esoa.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esoa.demo.entity.Contact;
import com.esoa.demo.entity.User;
import com.esoa.demo.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final EmailService emailService;


    @Transactional
    public void create(Contact dto,User user) {
        
        Contact contact = new Contact();
        contact.setTitle(dto.getTitle());
        contact.setUser(user);
        contact.setDescription(dto.getDescription());
        contact.setDischargeDate(LocalDate.now());
        contact.setDeleted(false);

        contactRepository.save(contact);
        emailService.sendGreateful(user.getEmail());
    }


    @Transactional(readOnly = true)
    public Contact getById(Integer id) {
        return contactRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void enableById(Integer id) {
        contactRepository.enableById(id);
    }

    @Transactional
    public void deleteById(Integer id) {
        contactRepository.deleteById(id);
    }

}
