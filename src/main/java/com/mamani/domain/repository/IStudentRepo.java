package com.mamani.domain.repository;

import com.mamani.domain.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepo extends IGenericRepo<Student, Long>{
}
