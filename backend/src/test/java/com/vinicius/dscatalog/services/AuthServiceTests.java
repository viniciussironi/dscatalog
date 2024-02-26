package com.vinicius.dscatalog.services;

import com.vinicius.dscatalog.entities.PasswordRecover;
import com.vinicius.dscatalog.entities.User;
import com.vinicius.dscatalog.factories.Factory;
import com.vinicius.dscatalog.factories.PasswordRecoverFactory;
import com.vinicius.dscatalog.repositories.PasswordRecoverRepository;
import com.vinicius.dscatalog.repositories.UserRepository;
import com.vinicius.dscatalog.utils.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

    @InjectMocks
    private AuthService service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordRecoverRepository passwordRecoverRepository;
    @Mock
    private UserUtil userUtil;
    @Mock
    private EmailService emailService;

    private String existsUsername, nonExistisUsername;
    private User user;
    private PasswordRecover passwordRecover;


    @BeforeEach
    void setUp() throws Exception {
        existsUsername = "maria@gmail.com";
        nonExistisUsername = "test@testando.com";
        user = Factory.createCustomOperatorUser(1L, existsUsername);
        passwordRecover = PasswordRecoverFactory.createPasswordRecover();

        when(userRepository.findByEmail(existsUsername)).thenReturn(user);
        when(userRepository.findByEmail(nonExistisUsername)).thenThrow(UsernameNotFoundException.class);

        when(passwordRecoverRepository.save(any())).thenReturn(passwordRecover);
    }

    //@Test
    public void authenticatedShouldReturnUserWhenExistisUsername() {
        when(userUtil.getLoggedUsername()).thenReturn(existsUsername);
        User result = service.authenticated();
        Assertions.assertNotNull(result);
    }

    //@Test
    public void authenticatedShouldReturnUsernameNotFoundExceptionWhenNonExistisUsername() {
        when(userUtil.getLoggedUsername()).thenReturn(nonExistisUsername);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.authenticated();
        });
    }
}
