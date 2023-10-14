package com.br.testenetdeal.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EncriptadorTest {

    private final String _SENHA = "password123";
    @Test
    public void test_encode_password() {
        String encodedPassword = Encriptador.encode(_SENHA);
        assertNotNull(encodedPassword);
        assertNotEquals(_SENHA, encodedPassword);
    }

    @Test
    public void test_check_password() {
        String encodedPassword = Encriptador.encode(_SENHA);
        assertTrue(Encriptador.checkSenha(_SENHA, encodedPassword));
    }

    @Test
    public void test_incorrect_password() {
        String encodedPassword = Encriptador.encode(_SENHA);
        assertFalse(Encriptador.checkSenha("incorrectPassword", encodedPassword));
    }

    @Test
    public void test_incorrect_password_hash() {
        assertThrows(IllegalArgumentException.class, () -> Encriptador.checkSenha(_SENHA, "incorrectPasswordHash"));
    }

}
