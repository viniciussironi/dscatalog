package com.vinicius.dscatalog.factories;

import com.vinicius.dscatalog.entities.PasswordRecover;

import java.time.Instant;

public class PasswordRecoverFactory {

    public static PasswordRecover createPasswordRecover() {
        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setId(1L);
        passwordRecover.setToken("token");
        passwordRecover.setEmail("maria@gmail.com");
        passwordRecover.setExpiration(Instant.now().plusMillis(10000L));
        return passwordRecover;
    }
}
