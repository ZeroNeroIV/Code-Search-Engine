package com.zerowhisper.codesearchengine.JavaExtractors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.zerowhisper.codesearchengine.Analyzers.JavaAnalyzerService;
import com.zerowhisper.codesearchengine.models.MFile;
import com.zerowhisper.codesearchengine.models.MStruct;
import com.zerowhisper.codesearchengine.repositories.StructRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class StructExtractor {

    private final StructRepository structRepository;
    private final MethodExtractor methodExtractor;
    private final VariableExtractor variableExtractor;
    private final JavaAnalyzerService javaAnalyzerService;

    @Autowired
    public StructExtractor(StructRepository structRepository, MethodExtractor methodExtractor, VariableExtractor variableExtractor, JavaAnalyzerService javaAnalyzerService) {
        this.structRepository = structRepository;
        this.methodExtractor = methodExtractor;
        this.variableExtractor = variableExtractor;
        this.javaAnalyzerService = javaAnalyzerService;
    }


    public void process(MFile mFile, CompilationUnit compilationUnit) {
            compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {

                MStruct token = new MStruct();
                token.setStructValue(classDeclaration.getNameAsString());
                token.setFile(mFile);

                if(classDeclaration.isAbstract()){

                    token.setStructType("Class");
                }
                else if(classDeclaration.isInterface()){
                    token.setStructType("Interface");
                }
                else{
                    token.setStructType("Class");
                }

                //Inheritance
//                classDeclaration.getExtendedTypes().forEach(extendedType -> {
//                    token.inheritance = extendedType.getNameAsString();
//                });

                //Composition
//                classDeclaration.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
//                    List<String> compositionStrings = new ArrayList<>();
//                    fieldDeclaration.getVariables().forEach(variable -> {
//                        if (fieldDeclaration.getElementType() instanceof ClassOrInterfaceType) {
//                            String compositionClass = fieldDeclaration.getElementType().toString();
//                            compositionStrings.add(compositionClass + ";");
//                        }
//                    });
//                    token.composition = compositionStrings;
//                });

                classDeclaration.getRange().ifPresent(range -> {
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
                    token.setPosition(jsonNode);
                });
                //==============================
                token.setAnalyzedStructValue(javaAnalyzerService.analyze(token.getStructValue()));
                structRepository.save(token);
                methodExtractor.process(token,classDeclaration );
                variableExtractor.processFromClass(token,classDeclaration);
            });


    }
}
