package com.zerowhisper.codesearchengine.services;

import com.zerowhisper.codesearchengine.Parsers.JavaParsingService;
import com.zerowhisper.codesearchengine.models.MFile;
import com.zerowhisper.codesearchengine.models.MProject;
import com.zerowhisper.codesearchengine.repositories.FileRepository;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcessingService {

    private final FileRepository fileRepository;
    @Autowired
    public FileProcessingService(FileRepository fileRepository, JavaParsingService javaParsingService) {
        this.fileRepository = fileRepository;
    }

    public List<MFile> getFileContent(Object[] objects) {
        List<MFile> fileContents = new ArrayList<>();
        List<String> filesPath= (List<String>) objects[0];
        MProject project= (MProject) objects[1];
        try {
            for (String path : filesPath) {

                String fileName = path.substring(path.lastIndexOf("\\") + 1);
                String fileContent = Files.readString(Paths.get(path));


                //Save the File in DB
                MFile mFile = new MFile();
                mFile.setName(fileName);
                mFile.setPath(path);
                mFile.setUploadTime(LocalDateTime.now());
                //mFile.setContent(fileContent);
                mFile.setLanguage(fileTypeIdentifier(path));
                mFile.setProject(project);
                fileRepository.save(mFile);

                //Add The File To the List To Pass it To the parser
                fileContents.add(mFile);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String path : filesPath) {
            System.out.println(path);
        }
        return fileContents;

    }

    public String fileTypeIdentifier(String filePath) {
        Tika tika = new Tika();
        File file = new File(filePath);

        try {
            return tika.detect(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
