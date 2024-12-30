package com.zerowhisper.codesearchengine.services;


import com.zerowhisper.codesearchengine.models.MFile;
import com.zerowhisper.codesearchengine.models.MProject;
import com.zerowhisper.codesearchengine.repositories.FileRepository;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcessingService {

    private final FileRepository fileRepository;
    @Autowired
    public FileProcessingService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public String getFileContent(List<String> filesPath, MProject project) {
        List<String> fileContents = new ArrayList<>();

        try {
            for (String path : filesPath) {

                String fileName = path.substring(path.lastIndexOf("\\") + 1);
                String fileContent=Files.readString(Paths.get(path));

                //Add The File To the List To Pass it To the parser
                fileContents.add(fileContent);

                //Save the File in DB
                MFile mFile = new MFile();
                mFile.setName(fileName);
                mFile.setPath(path);
                mFile.setUploadTime(LocalDateTime.now());
                mFile.setContent(fileContent);
                mFile.setLanguage(fileTypeIdentifier(path));
                mFile.setProject(project);
                fileRepository.save(mFile);

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        for (String path : filesPath) {
            System.out.println(path);
        }
        //Pass the Content To Java Parser
        return "Files Content extracted Successfully";
    }

    public String fileTypeIdentifier(String filePath){
        Tika tika = new Tika();
        File file = new File(filePath);


        try {
            return tika.detect(file);
        }catch (IOException e){
            e.printStackTrace();
            return "Error";
        }
    }
}
