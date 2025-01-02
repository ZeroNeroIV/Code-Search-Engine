package com.zerowhisper.codesearchengine.JavaExtractors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.zerowhisper.codesearchengine.Analyzers.JavaAnalyzerService;
import com.zerowhisper.codesearchengine.models.MMethod;
import com.zerowhisper.codesearchengine.models.MStruct;
import com.zerowhisper.codesearchengine.repositories.MethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MethodExtractor  {

   private final MethodRepository methodRepository;
   private final VariableExtractor variableExtractor;
   private final LoopExtractor loopExtractor;
   private final JavaAnalyzerService javaAnalyzerService;

   @Autowired
    public MethodExtractor(MethodRepository methodRepository, VariableExtractor variableExtractor, LoopExtractor loopExtractor, JavaAnalyzerService javaAnalyzerService) {
        this.methodRepository = methodRepository;
        this.variableExtractor = variableExtractor;
        this.loopExtractor = loopExtractor;
        this.javaAnalyzerService = new JavaAnalyzerService();
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
                mMethod.setAnalyzedMethodValue(methodDeclaration.getNameAsString());
                methodRepository.save(mMethod);

                variableExtractor.processFromMethod(mMethod, methodDeclaration);
                loopExtractor.process(mMethod, methodDeclaration);
                List<String> listOfTokensForTheAnalyzedName=javaAnalyzerService.analyze(mMethod.getMethodValue());
                if(listOfTokensForTheAnalyzedName.size()>1){//this if checks the word even needs filtering

                    for (String token : listOfTokensForTheAnalyzedName) {
                        if(token.equals(mMethod.getMethodValue())){
                            continue;
                        }
                        MMethod tempMthMethod = new MMethod();
                        tempMthMethod.setMethodValue(mMethod.getMethodValue());
                        tempMthMethod.setStruct(mStruct);
                        tempMthMethod.setPosition(mMethod.getPosition());
                        tempMthMethod.setReturnType(mMethod.getReturnType());
                        tempMthMethod.setAnalyzedMethodValue(token);
                        methodRepository.save(tempMthMethod);
                    }
                }
            });

    }

}
