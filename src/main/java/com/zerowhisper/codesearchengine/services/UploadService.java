package com.zerowhisper.codesearchengine.services;

import com.zerowhisper.codesearchengine.models.MProject;
import com.zerowhisper.codesearchengine.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.*;


@Service

public class UploadService {

    private final ProjectRepository projectRepository;
    private final FileProcessingService fileProcessingService;

    @Autowired
    public UploadService(ProjectRepository projectRepository, FileProcessingService fileProcessingService) {
        this.projectRepository = projectRepository;
        this.fileProcessingService = fileProcessingService;
    }


    public Object[] unzipFile(MultipartFile zipFile) throws IOException {


        //TODO MAKE sure to change the saving directory name

        //This line will make a Temp Directory in the system default directory
        //Original Path + The File Name
        Path tempDir = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), "Uploaded_Files_");

        //Create MProject to save the upload data
        MProject project = new MProject();
        project.setName(zipFile.getOriginalFilename());
        project.setPath(tempDir.toString());
        project.setUploadTime(LocalDateTime.now());
        projectRepository.save(project);


        //This the List of the files path's will pass to the file service
        List<String> filePaths = new ArrayList<>();

        try (InputStream inputStream = zipFile.getInputStream();
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

            ZipEntry entry;

            //iterate throw zipFile
            while ((entry = zipInputStream.getNextEntry()) != null) {
                //get the file path and add it to the list

                Path extractedFilePath = tempDir.resolve(entry.getName());


                // If the entry is a file, extract it
                if (!entry.isDirectory()) {

                    // Add the full path to the list
                    filePaths.add(extractedFilePath.toString());
                    Files.createDirectories(extractedFilePath.getParent());

                    try (OutputStream outputStream = Files.newOutputStream(extractedFilePath)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zipInputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, len);
                        }
                    }
                }
            }

            return new Object[] {filePaths,project};


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
