package org.kiennguyenfpt.datingapp.configs;

import com.google.firebase.FirebaseApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
//        InputStream serviceAccount =
//                getClass()
//                        .getClassLoader()
//                        .getResourceAsStream("datingwebfpt-firebase-adminsdk-grvca-d65d9a7351.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                //.setStorageBucket("datingapp-f4ecb.appspot.com")
//                .setStorageBucket("datingwebexe")
//                .build();
//
//        return FirebaseApp.initializeApp(options);
    }

}
