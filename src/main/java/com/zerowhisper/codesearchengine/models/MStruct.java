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
public class MStruct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String structType;

    @Column
    private String structValue;


    @Column
    private String analyzedStructValue;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode position; // as json string

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MFile.class)
    @JoinColumn(name = "File_id", nullable = false)
    private MFile file;

    // Inheritance
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MStruct.class)
    @JoinColumn(name = "Inherited_from")
    private MStruct inheritance;

    // Composition
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MStruct.class)
    @JoinColumn(name = "CompostionWith")
    private MStruct composition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStructType() {
        return structType;
    }

    public void setStructType(String structType) {
        this.structType = structType;
    }

    public String getStructValue() {
        return structValue;
    }

    public void setStructValue(String structValue) {
        this.structValue = structValue;
    }

    public JsonNode getPosition() {
        return position;
    }

    public void setPosition(JsonNode position) {
        this.position = position;
    }

    public MFile getFile() {
        return file;
    }

    public void setFile(MFile file) {
        this.file = file;
    }

    public MStruct getInheritance() {
        return inheritance;
    }

    public void setInheritance(MStruct inheritance) {
        this.inheritance = inheritance;
    }

    public MStruct getComposition() {
        return composition;
    }

    public void setComposition(MStruct composition) {
        this.composition = composition;
    }
    public String getAnalyzedStructValue() {
        return analyzedStructValue;
    }

    public void setAnalyzedStructValue(String analyzedStructValue) {
        this.analyzedStructValue = analyzedStructValue;
    }

}
