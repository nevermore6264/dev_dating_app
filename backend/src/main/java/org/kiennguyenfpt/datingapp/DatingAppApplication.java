package org.kiennguyenfpt.datingapp;

import org.kiennguyenfpt.datingapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatingAppApplication {
    //@Autowired
   // private JwtUtil jwtUtil;
    public static void main(String[] args) {
        SpringApplication.run(DatingAppApplication.class, args);
        //JwtUtil jwtUtil = new JwtUtil();
        //System.out.println("JWT Secret: " + jwtUtil.getSecret());
    }
}
