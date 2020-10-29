
package com.app.covidfree.service;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;


@Service
public class MobilUserService {



    public String generateKey(Integer citizenId) {
        String salt = "FFFFFFFF";
        String mypassword = "AAAAAAAA";
        TextEncryptor encryptor = Encryptors.text(mypassword, salt);
        String cipherText = encryptor.encrypt(String.valueOf(citizenId));
        return cipherText;
    }

}
