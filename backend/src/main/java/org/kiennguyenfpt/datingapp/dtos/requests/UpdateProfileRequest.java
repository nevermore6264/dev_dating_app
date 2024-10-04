package org.kiennguyenfpt.datingapp.dtos.requests;

import org.kiennguyenfpt.datingapp.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UpdateProfileRequest {
    private List<MultipartFile> files;
    private String name;
    private Integer age;
    private String bio;
    private Gender gender;
    private String avatar;
    private String phone;
}
