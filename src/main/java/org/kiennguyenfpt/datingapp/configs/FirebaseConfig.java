package org.kiennguyenfpt.datingapp.configs;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("D:\\Kien FPT\\Semester 8\\EXE201\\DatingApp\\datingapp-f4ecb-firebase-adminsdk-moeb5-7529827c9d.json"); // Cập nhật đường dẫn này nếu cần

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("datingapp-f4ecb.appspot.com") // Cập nhật tên bucket này nếu cần
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public StorageClient storageClient(FirebaseApp firebaseApp) {
        return StorageClient.getInstance(firebaseApp);
    }
}
