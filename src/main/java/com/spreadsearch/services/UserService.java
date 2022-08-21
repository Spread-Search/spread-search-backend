package com.spreadsearch.services;

import com.spreadsearch.exceptions.UserExistException;
import com.spreadsearch.models.User;
import com.spreadsearch.models.enums.Role;
import com.spreadsearch.payload.request.SingUpRequest;
import com.spreadsearch.payload.response.MessageResponse;
import com.spreadsearch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collections;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SendMailService sendMailService;

    @Value("${app.host}")
    private String host;

    public MessageResponse createUser(SingUpRequest singUpRequest) {
        String activationCode = UUID.randomUUID().toString();
        String activateLink = host + "/api/auth/activate/" + activationCode;
        String email = singUpRequest.getEmail();
        User user = new User();
        user.setEmail(singUpRequest.getEmail());
        user.setUsername(singUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(singUpRequest.getPassword()));
        user.getRoles().add(Role.USER);
        user.setActivateCode(activationCode);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        sendMailService.sendEmail("Подтверждение аккаунта SpreadSearch", getText(activateLink) , email);
        return new MessageResponse("Message with activation code has been sent to email " + email);
    }

    private String getText(String activateLink) {
        return "Для подтверждения аккаунта пожалуйста перейдите по ссылки " + activateLink;
    }

    public MessageResponse activateUser(String activationCode) {
        boolean activate = false;
        for (User user : userRepository.findAll()) {
            if (user.getActivateCode().equals(activationCode)) {
                user.setActivateCode("activate");
                user.setActive(true);
                userRepository.save(user);
                activate = true;
            }
        }
        return activate ? new MessageResponse("User activate success")
                : new MessageResponse("Activate code is not found");
    }
}
