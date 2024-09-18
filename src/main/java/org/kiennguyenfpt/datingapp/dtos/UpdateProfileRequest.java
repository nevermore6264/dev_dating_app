package org.kiennguyenfpt.datingapp.dtos;

import org.kiennguyenfpt.datingapp.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    private String name;
    private Integer age;
    private String bio;
    private Gender gender;
}
