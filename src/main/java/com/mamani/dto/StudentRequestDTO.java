package com.mamani.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentRequestDTO implements Serializable {

    @NotBlank(message = "documentId is mandatory")
    @Pattern(regexp = "^[0-9]{8}$", message = "documentId must be 8 digits")
    private String documentId;

    @NotBlank(message = "name is mandatory")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "name must contain only letters and spaces")
    private String name;

    @NotBlank(message = "lastname is mandatory")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "name must contain only letters and spaces")
    private String lastname;

    @NotNull(message = "age is mandatory")
    @Digits(integer = 3, fraction = 0, message = "age must be a number with up to 3 digits")
    @Min(value = 6, message = "age must be at least 6")
    @Max(value = 150, message = "age must be at most 150")
    private Integer age;

}
