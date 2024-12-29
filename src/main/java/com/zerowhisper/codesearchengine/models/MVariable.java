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
public class MVariable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variableId;

    @Column
    private String variableValue;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode position; // as json string

    @Column
    private String containedAt;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = MMethod.class)
    private  Long methodId;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = MStruct.class)
    private  Long structId;

    @Column
    private String dataType;

}
