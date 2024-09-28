package org.kiennguyenfpt.datingapp.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailRequest {
    private String to;
    private String subject;
    private String text;
}
