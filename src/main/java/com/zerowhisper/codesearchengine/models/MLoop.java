package com.zerowhisper.codesearchengine.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

@Entity
@Table
@Data
public class MLoop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loopId;

    @Column
    private String loopType;

    @Column
    private Long startAt;

    @Column
    private Long endAt;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode position; // as json string

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = MMethod.class)
    private Long methodId;
}
