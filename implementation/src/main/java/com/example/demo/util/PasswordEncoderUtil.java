package com.example.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "geektaro";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);  // これがBCrypt形式でエンコードされたパスワードです
    }
}
