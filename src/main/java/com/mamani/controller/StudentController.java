package com.mamani.controller;

import com.mamani.dto.StudentRequestDTO;
import com.mamani.dto.StudentResponseDTO;
import com.mamani.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Validated
@Slf4j
public class StudentController {

    private final IStudentService studentService;

    @GetMapping
    public Mono<ResponseEntity<List<StudentResponseDTO>>> getAllStudents(){

        return studentService.readAll()
                        .collectList()
                        .map(list -> {
                            if(list.isEmpty())
                                return ResponseEntity.noContent().build();
                            else
                                return ResponseEntity.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(list);
                        });
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDto, final ServerHttpRequest req){

        log.info("StudentController-createStudent request => {}", studentRequestDto.toString());

        return studentService.save(studentRequestDto)
                .doOnSuccess(resp -> {
                    log.info("StudentController-createStudent response => {}", resp.toString());
                    log.info("Student created successfully ID : {}", resp.getId());
                })
                .map(response -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(response.getId().toString())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());
    }
}
