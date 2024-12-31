package com.zerowhisper.codesearchengine.Utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.zerowhisper.codesearchengine.models.*;
import com.zerowhisper.codesearchengine.repositories.VariableRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VariableExtractor  {

    private final VariableRepository variableRepository;
    @Autowired
    public VariableExtractor(VariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    public void processFromClass(MStruct mFile, ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
            classOrInterfaceDeclaration.getFields().forEach(f -> {
                f.getVariables().forEach(v -> {
                    MVariable variable = new MVariable();
                    variable.setVariableValue(v.getNameAsString());
                    variable.setStruct(mFile);
                    variable.setMethod(null);
                    variable.setDataType(v.getType().toString());
                    variable.setContainedAt("Class");

                    v.getRange().ifPresent(range -> {
                        Map<String, Object> position = new HashMap<>();
                        position.put("StartRow", range.begin.line);
                        position.put("EndRow", range.end.line);
                        position.put("StartColumn", range.begin.column);
                        position.put("EndColumn", range.end.column);

                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode jsonNode = mapper.valueToTree(position);
                        variable.setPosition(jsonNode);
                    });
                    variableRepository.save(variable);
                });
            });
    }
    public void processFromMethod(MMethod mMethod, MethodDeclaration methodDeclaration) {
        methodDeclaration.findAll(VariableDeclarationExpr.class).forEach(variableDeclarationExpr -> {
            variableDeclarationExpr.getVariables().forEach(variable -> {
               MVariable mVariable = new MVariable();
                mVariable.setVariableValue(variable.getNameAsString());
                mVariable.setDataType(variable.getTypeAsString());
                mVariable.setContainedAt("Method");

                // Get the position of the variable
                variable.getRange().ifPresent(range -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("StartRow", range.begin.line);
                    map.put("EndRow", range.end.line);
                    map.put("StartColumn", range.begin.column);
                    map.put("EndColumn", range.end.column);

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.valueToTree(map);
                    mVariable.setPosition(jsonNode);
                });
                mVariable.setMethod(mMethod);
                mMethod.setStruct(null);
                variableRepository.save(mVariable);
            });
        });
    }
}
