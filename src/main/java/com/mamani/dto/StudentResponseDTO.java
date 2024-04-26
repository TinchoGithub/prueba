package com.mamani.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentResponseDTO implements Serializable {
    private Long id;
    private String documentId;
    private String name;
    private String lastname;
    private boolean status;
    private int age;
    private String createdAt;
}
