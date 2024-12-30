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
    @JoinColumn(name = "method_id", nullable = false)
    private  MMethod method;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = MStruct.class)
    @JoinColumn(name = "struct_id", nullable = false)
    private  MStruct struct;

    @Column
    private String dataType;

}
