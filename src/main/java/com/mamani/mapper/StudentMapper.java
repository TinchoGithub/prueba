package com.mamani.mapper;

import com.mamani.domain.entity.Student;
import com.mamani.dto.StudentRequestDTO;
import com.mamani.dto.StudentResponseDTO;

public class StudentMapper {

    public static StudentResponseDTO convertToResponseDto(Student student){
        return StudentResponseDTO.builder()
                .id(student.getId())
                .documentId(student.getDocumentId())
                .name(student.getName())
                .lastname(student.getLastname())
                .age(student.getAge())
                .createdAt(student.getCreatedAt())
                .status(student.isStatus())
                .build();
    }

    public static Student convertToEntity(StudentRequestDTO studentRequestDTO){
        return Student.builder()
                .documentId(studentRequestDTO.getDocumentId())
                .name(studentRequestDTO.getName())
                .lastname(studentRequestDTO.getLastname())
                .age(studentRequestDTO.getAge())
                .build();
    }
}
