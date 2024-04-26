package com.mamani.service.impl;

import com.mamani.domain.entity.Student;
import com.mamani.domain.repository.IStudentRepo;
import com.mamani.dto.StudentRequestDTO;
import com.mamani.dto.StudentResponseDTO;
import com.mamani.exception.CustomException;
import com.mamani.mapper.StudentMapper;
import com.mamani.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final static String MESSAGE = "Student with the same document already exists, and registration could not be performed";
    private final IStudentRepo studentRepo;

    @Override
    public Mono<StudentResponseDTO> save(StudentRequestDTO studentRequestDTO){

        return existsStudentByDocumentId(studentRequestDTO.getDocumentId())
                .filter(exists -> !exists)
                .flatMap(exists -> {
                    Student student = StudentMapper.convertToEntity(studentRequestDTO);
                    student.setCreatedAt(LocalDate.now().toString());
                    student.setStatus(true);
                    return studentRepo.save(student).map(StudentMapper::convertToResponseDto);
                })
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST,MESSAGE)));
    }

    @Override
    public Flux<StudentResponseDTO> readAll() {
        return studentRepo.findAll()
                .filter(Student::isStatus)
                .map(StudentMapper::convertToResponseDto);
    }

    @Override
    public Mono<StudentResponseDTO> readById(Long id) {
        return studentRepo.findById(id)
                .map(StudentMapper::convertToResponseDto);
    }

    @Override
    public Mono<Boolean> existsStudentByDocumentId(String documentId) {
        return studentRepo.findAll()
                .any(student -> student.getDocumentId().equals(documentId))
                .switchIfEmpty(Mono.just(false));
    }

    @Override
    public Mono<StudentResponseDTO> update(StudentRequestDTO studentRequestDTO) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return null;
    }

}
