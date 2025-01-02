package com.zerowhisper.codesearchengine.JavaFilters;

import com.zerowhisper.codesearchengine.Interfaces.JavaFilters;

import java.util.ArrayList;
import java.util.List;

public class CaseInsensitiveFilter implements JavaFilters {
    @Override
    public List<String> doFilter(List<String> token) {

        if(token.isEmpty()){
            return token;
        }

       List<String> filteredToken = new ArrayList<String>();
        for (String word : token) {
            filteredToken.add(word.toLowerCase());
        }

        token.addAll(filteredToken);
        return token;
    }
}
