package com.zerowhisper.codesearchengine.Parsers;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.zerowhisper.codesearchengine.Interfaces.ParsingService;
import com.zerowhisper.codesearchengine.JavaExtractors.StructExtractor;
import com.zerowhisper.codesearchengine.models.MFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service

public class JavaParsingService implements ParsingService {

   private final StructExtractor structExtractor;

   @Autowired
    public JavaParsingService(StructExtractor structExtractor) {
        this.structExtractor = structExtractor;
    }


    @Override
    public void processFile(List<MFile> files) {


        CompilationUnit compilationUnit=new CompilationUnit();

        for(MFile file: files) {
            try {

            file.setPath(file.getPath().replace("\\", "/"));

            File javaFile = new File(file.getPath());
            compilationUnit = StaticJavaParser.parse(javaFile);
            structExtractor.process(file,compilationUnit);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
