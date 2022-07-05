package com.esoa.demo.service;

import com.esoa.demo.entity.User;
import com.esoa.demo.enumeration.Role;
import com.esoa.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class UserService  implements UserDetailsService  {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;

    @Transactional
    public void create(User dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("There is already a user associated with the email entered");

        User user = new User();

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setPassword(encoder.encode(dto.getPassword()));  //dependencia de seguridad
        if (userRepository.findAll().isEmpty()) user.setRole(Role.ADMIN);
        else user.setRole(Role.USER);
        System.out.println(dto.getRole());
        userRepository.save(user);
        if (userRepository.existsByEmail(user.getEmail())) emailService.send(dto.getEmail());
    }

    @Transactional
    public void update(User dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("There is already a user associated with the email entered");

        User user = userRepository.findById(dto.getId()).get();

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setPassword(encoder.encode(dto.getPassword()));  //dependencia de seguridad

        if (userRepository.findAll().isEmpty()) user.setRole(Role.ADMIN);
        else user.setRole(dto.getRole());

        emailService.send(dto.getEmail());    //dependencia de email

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void enableById(Integer id) {
        userRepository.enableById(id);
    }

    @Transactional
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user associated with the email entered"));
        GrantedAuthority authority = () -> "ROLE_" + user.getRole().name();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("id", user.getId());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole().name());
        session.setAttribute("name", user.getName());
        session.setAttribute("lastname", user.getLastName());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), singletonList(authority));
    }
    

}
