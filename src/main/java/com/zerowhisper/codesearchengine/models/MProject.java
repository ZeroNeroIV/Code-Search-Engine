package com.zerowhisper.codesearchengine.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table
@Entity
public class MProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private LocalDateTime uploadTime;
}
