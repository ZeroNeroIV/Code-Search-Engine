package com.zerowhisper.codesearchengine.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
@Entity
public class MFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private LocalDateTime uploadTime;
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MProject.class)
    private Long projectId;
}
