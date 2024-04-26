package com.mamani.controller;

import com.mamani.dto.StudentRequestDTO;
import com.mamani.dto.StudentResponseDTO;
import com.mamani.service.IStudentService;
import com.mamani.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = StudentController.class)
@Import(StudentServiceImpl.class)
class StudentControllerTest {

    @Autowired
    private WebTestClient clientHttp;

    @MockBean
    private ConnectionFactoryInitializer connectionFactoryInitializer;

    @MockBean
    private IStudentService studentService;

    @MockBean
    private Resources resources;

    private StudentResponseDTO studentTest1;
    private StudentResponseDTO studentTest2;
    private StudentResponseDTO studentTest3;
    private List<StudentResponseDTO> list;
    private StudentRequestDTO requestDto;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);

        studentTest1 = StudentResponseDTO.builder()
                .id(1L)
                .documentId("12345678")
                .name("Aldo Nelson")
                .lastname("Mamani Mamani")
                .status(true)
                .age(7)
                .createdAt(LocalDate.now().toString())
                .build();

        studentTest2 = StudentResponseDTO.builder()
                .id(1L)
                .documentId("12345578")
                .name("Aldo Nelson")
                .lastname("Mamani Mamani")
                .status(true)
                .age(7)
                .createdAt(LocalDate.now().toString())
                .build();

        studentTest3 = StudentResponseDTO.builder()
                .id(1L)
                .documentId("12344678")
                .name("Aldo Nelson")
                .lastname("Mamani Mamani")
                .status(true)
                .age(7)
                .createdAt(LocalDate.now().toString())
                .build();

        requestDto = StudentRequestDTO.builder()
                .documentId("12345678")
                .name("Aldo Nelson")
                .lastname("Mamani Mamani")
                .age(7)
                .build();

        list = new ArrayList<>();
        list.add(studentTest1);
        list.add(studentTest2);
        list.add(studentTest3);
    }

    @Test
    public void getAllStudents_EmptyList() {

        Mockito.when(studentService.readAll()).thenReturn(Flux.empty());

        clientHttp.get()
                .uri("/api/v1/students")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent()
                .expectBodyList(StudentResponseDTO.class)
                .hasSize(0);
    }

    @Test
    public void getAllStudents_NonEmptyList() {

        Mockito.when(studentService.readAll()).thenReturn(Flux.fromIterable(list));

        clientHttp.get()
                .uri("/api/v1/students")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(StudentResponseDTO.class)
                .hasSize(3);
    }

    @Test
    void createStudent_Success() {

        Mockito.when(studentService.save(requestDto)).thenReturn(Mono.just(studentTest1));

        clientHttp.post().uri("/api/v1/students")
                .body(Mono.just(requestDto), StudentRequestDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBody()
//                .isEmpty();
    }

}