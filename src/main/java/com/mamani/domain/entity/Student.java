package com.mamani.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    private Long id;

    @NotEmpty
    @Column("document_id")
    private String documentId;

    @NotEmpty
    @Column("name")
    private String name;

    @NotEmpty
    @Column("lastname")
    private String lastname;

    @NotNull
    @Column("status")
    private boolean status;

    @NotNull
    @Column("age")
    private int age;

    @NotNull
    @Column("created_at")
    private String createdAt;

}
