package com.zerowhisper.codesearchengine.Analyzers;

import com.zerowhisper.codesearchengine.Interfaces.Analyzer;
import com.zerowhisper.codesearchengine.Interfaces.JavaFilters;
import com.zerowhisper.codesearchengine.JavaFilters.caseInsensitiveFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JavaAnalyzerService implements Analyzer {

    @Override
    public String analyze(String token){

        //ToDo add the filters
        List<JavaFilters>javaFilters=List.of(new caseInsensitiveFilter());

        for(JavaFilters javaFilters1:javaFilters){
            token=javaFilters1.doFilter(token);
        }
        return token;
    }
}
