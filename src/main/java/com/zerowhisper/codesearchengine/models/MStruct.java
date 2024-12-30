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
    private String structName;

    @Column
    private String structType;

    @Column
    private String structValue;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JsonNode position; // as json string

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MFile.class)
    @JoinColumn(name = "File_id", nullable = false)
    private MFile file;

    // Inheritance
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MStruct.class)
    @JoinColumn(name = "Inherited_from", nullable = false)
    private MStruct inheritance;

    // Composition
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MStruct.class)
    @JoinColumn(name = "CompostionWith", nullable = false)
    private MStruct composition;

}
