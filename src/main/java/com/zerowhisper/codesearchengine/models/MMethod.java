package com.zerowhisper.codesearchengine.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;


@Table
@Entity
public class MMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long methodId;

    @Column
    private String methodValue;


    @Column
    private String analyzedMethodValue;

    @Column
    private String returnType;


    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode position; // as json string

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MStruct.class)
    @JoinColumn(name = "Struct_id")
    private MStruct struct;

    public Long getMethodId() {
        return methodId;
    }

    public void setMethodId(Long methodId) {
        this.methodId = methodId;
    }

    public String getMethodValue() {
        return methodValue;
    }

    public void setMethodValue(String methodValue) {
        this.methodValue = methodValue;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public JsonNode getPosition() {
        return position;
    }

    public void setPosition(JsonNode position) {
        this.position = position;
    }

    public MStruct getStruct() {
        return struct;
    }

    public void setStruct(MStruct struct) {
        this.struct = struct;
    }
    public String getAnalyzedMethodValue() {
        return analyzedMethodValue;
    }

    public void setAnalyzedMethodValue(String analyzedMethodValue) {
        this.analyzedMethodValue = analyzedMethodValue;
    }

}
