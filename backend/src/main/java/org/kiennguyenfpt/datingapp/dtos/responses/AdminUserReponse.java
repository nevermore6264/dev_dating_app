package org.kiennguyenfpt.datingapp.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserReponse {
    private long userId;
    private String email;
    private String phone;
    private String roleName;
    private String status;
    private Timestamp createdAt;
}
