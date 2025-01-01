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
    @JoinColumn(name = "method_id")
    private  MMethod method;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = MStruct.class)
    @JoinColumn(name = "struct_id")
    private  MStruct struct;

    @Column
    private String dataType;

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public JsonNode getPosition() {
        return position;
    }

    public void setPosition(JsonNode position) {
        this.position = position;
    }

    public String getContainedAt() {
        return containedAt;
    }

    public void setContainedAt(String containedAt) {
        this.containedAt = containedAt;
    }

    public MMethod getMethod() {
        return method;
    }

    public void setMethod(MMethod method) {
        this.method = method;
    }

    public MStruct getStruct() {
        return struct;
    }

    public void setStruct(MStruct struct) {
        this.struct = struct;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
