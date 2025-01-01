package com.zerowhisper.codesearchengine.Utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.zerowhisper.codesearchengine.models.MMethod;
import com.zerowhisper.codesearchengine.models.MStruct;
import com.zerowhisper.codesearchengine.repositories.MethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MethodExtractor  {

   private final MethodRepository methodRepository;
   private final VariableExtractor variableExtractor;
   private final LoopExtractor loopExtractor;

   @Autowired
    public MethodExtractor(MethodRepository methodRepository, VariableExtractor variableExtractor, LoopExtractor loopExtractor) {
        this.methodRepository = methodRepository;
        this.variableExtractor = variableExtractor;
        this.loopExtractor = loopExtractor;
    }

    public void process(MStruct mStruct, ClassOrInterfaceDeclaration clazz) {

            clazz.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {

                MMethod mMethod = new MMethod();
                mMethod.setMethodValue(methodDeclaration.getNameAsString());
                mMethod.setReturnType(methodDeclaration.getType().toString());

                methodDeclaration.getRange().ifPresent(range -> {
                    // 1. Make a Map<String, Object>
                    Map<String, Object> map = new HashMap<>();
                    // 2. put the entries into this map
                    map.put("StartRow", range.begin.line);
                    map.put("EndRow", range.end.line);

                    map.put("StartColumn", range.begin.column);
                    map.put("EndColumn", range.end.column);

                    // 3. use toString() then you are done
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.valueToTree(map);
                    mMethod.setPosition(jsonNode);
                    mMethod.setStruct(mStruct);
                });
                //============================
                methodRepository.save(mMethod);
                variableExtractor.processFromMethod(mMethod,methodDeclaration);
                loopExtractor.process(mMethod,methodDeclaration);
            });

    }

}
