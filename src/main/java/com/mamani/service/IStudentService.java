package com.mamani.service;

import com.mamani.dto.StudentRequestDTO;
import com.mamani.dto.StudentResponseDTO;
import reactor.core.publisher.Mono;

public interface IStudentService extends ICRUD<StudentRequestDTO, StudentResponseDTO,Long>{
    Mono<Boolean> existsStudentByDocumentId(String documentId) ;
}
