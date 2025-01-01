package com.zerowhisper.codesearchengine.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;

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
    private String Initialization;

    @Column
    private String Condition;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> Updates;


    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode position; // as json string

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = MMethod.class)
    @JoinColumn(name = "method_id", nullable = false)
    private MMethod method;

    public Long getLoopId() {
        return loopId;
    }

    public void setLoopId(Long loopId) {
        this.loopId = loopId;
    }

    public String getLoopType() {
        return loopType;
    }

    public void setLoopType(String loopType) {
        this.loopType = loopType;
    }

    public String getInitialization() {
        return Initialization;
    }

    public void setInitialization(String initialization) {
        Initialization = initialization;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public List<String> getUpdates() {
        return Updates;
    }

    public void setUpdates(List<String> updates) {
        Updates = updates;
    }

    public JsonNode getPosition() {
        return position;
    }

    public void setPosition(JsonNode position) {
        this.position = position;
    }

    public MMethod getMethod() {
        return method;
    }

    public void setMethod(MMethod method) {
        this.method = method;
    }
}
