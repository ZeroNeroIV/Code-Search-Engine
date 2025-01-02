package com.zerowhisper.codesearchengine.services;

import com.zerowhisper.codesearchengine.Analyzers.JavaAnalyzerService;
import com.zerowhisper.codesearchengine.Parsers.JavaParsingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProcessService {
    private final FileProcessingService fileProcessingService;
    private final JavaParsingService javaParsingService;
    private final UploadService uploadService;
    @Autowired
    public ProcessService(FileProcessingService fileProcessingService, JavaParsingService javaParsingService, UploadService uploadService) {
        this.fileProcessingService = fileProcessingService;
        this.javaParsingService = javaParsingService;
        this.uploadService = uploadService;
    }

    @Transactional
    public void processPKG(MultipartFile pkgFile) throws IOException {
       javaParsingService.processFile(fileProcessingService.getFileContent(uploadService.unzipFile(pkgFile)));
    }

}
