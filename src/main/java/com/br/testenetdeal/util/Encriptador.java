package com.br.testenetdeal.util;

import org.mindrot.jbcrypt.BCrypt;

public class Encriptador {
    public static String encode(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public static boolean checkSenha(String senha, String senhaHash) {
        return BCrypt.checkpw(senha, senhaHash);
    }
}
