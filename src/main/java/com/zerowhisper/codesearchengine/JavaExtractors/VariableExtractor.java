package com.zerowhisper.codesearchengine.JavaExtractors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.zerowhisper.codesearchengine.Analyzers.JavaAnalyzerService;
import com.zerowhisper.codesearchengine.models.*;
import com.zerowhisper.codesearchengine.repositories.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VariableExtractor  {

    private final VariableRepository variableRepository;
    private final JavaAnalyzerService javaAnalyzerService;
    @Autowired
    public VariableExtractor(VariableRepository variableRepository, JavaAnalyzerService javaAnalyzerService) {
        this.javaAnalyzerService = javaAnalyzerService;
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
                    saveVariable(variable);
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

                saveVariable(mVariable);
            });
        });
    }
    public  void saveVariable(MVariable variable) {
       variableRepository.save(variable);
        List<String> listOfTokensForTheAnalyzedName = javaAnalyzerService.analyze(variable.getVariableValue());
        for (String token : listOfTokensForTheAnalyzedName) {
            if(variable.getVariableValue().equals(token)) {
                continue;
            }
            MVariable tempVariable = new MVariable();
            tempVariable.setVariableValue(variable.getVariableValue());
            tempVariable.setDataType(variable.getDataType());
            tempVariable.setContainedAt(variable.getContainedAt());
            tempVariable.setMethod(variable.getMethod());
            tempVariable.setPosition(variable.getPosition());

            variable.setAnalyzedVariableValue(token);
            variableRepository.save(tempVariable);
        }
    }
}
