package com.zerowhisper.codesearchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.zerowhisper.codesearchengine")
public class CodeSearchEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeSearchEngineApplication.class, args);
    }

}
