package com.zerowhisper.codesearchengine.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table
public class MMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long methodId;

    @Column
    private String methodValue;

    @Column
    private String returnType;

    @Column
    private String methodType;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode position; // as json string

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MStruct.class)
    private Long structId;
}
