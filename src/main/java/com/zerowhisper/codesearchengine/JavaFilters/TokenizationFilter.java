package com.zerowhisper.codesearchengine.JavaFilters;

import com.zerowhisper.codesearchengine.Interfaces.JavaFilters;

import java.util.ArrayList;
import java.util.List;

public class TokenizationFilter implements JavaFilters {


    @Override
    public List<String> doFilter(List<String> token) {

        if(token.isEmpty()){
            return token;
        }

        List<String>listAfterTokenization = new ArrayList<String>();
        for (String tokenStr : token) {
            String[] splitToken = tokenStr.split("_+");

            for (String splitTokenStr : splitToken) {
                listAfterTokenization.add(splitTokenStr);
            }
        }
        return listAfterTokenization;
    }
}
