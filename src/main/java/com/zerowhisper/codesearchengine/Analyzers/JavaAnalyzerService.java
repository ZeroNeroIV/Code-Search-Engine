package com.zerowhisper.codesearchengine.Analyzers;

import com.zerowhisper.codesearchengine.Interfaces.Analyzer;
import com.zerowhisper.codesearchengine.Interfaces.JavaFilters;
import com.zerowhisper.codesearchengine.JavaFilters.CaseInsensitiveFilter;
import com.zerowhisper.codesearchengine.JavaFilters.TokenizationFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JavaAnalyzerService implements Analyzer {

    @Override
    public List<String> analyze(String token){

        //ToDo add the filters
        //ToDo Find_Student --> find , student
        
        List<JavaFilters>javaFilters=List.of(new TokenizationFilter(),new CaseInsensitiveFilter());
        List<String>tokensList=List.of(token);

        for(JavaFilters javaFilters1:javaFilters){
            tokensList= javaFilters1.doFilter(tokensList);

        }
        return tokensList;
    }
}
