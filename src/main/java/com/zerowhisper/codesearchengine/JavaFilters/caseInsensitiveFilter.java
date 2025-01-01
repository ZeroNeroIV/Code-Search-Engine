package com.zerowhisper.codesearchengine.JavaFilters;

import com.zerowhisper.codesearchengine.Interfaces.JavaFilters;

public class caseInsensitiveFilter implements JavaFilters {
    @Override
    public String doFilter(String token) {
        return token.toLowerCase();
    }
}
