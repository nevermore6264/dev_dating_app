package org.kiennguyenfpt.datingapp.configs;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("D:\\DatingWeb\\FE Dating-App\\Dating-app\\datingapp-f4ecb-firebase-adminsdk-moeb5-7529827c9d.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("datingapp-f4ecb.appspot.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }

}
