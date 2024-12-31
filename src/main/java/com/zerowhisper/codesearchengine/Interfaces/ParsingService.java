package com.zerowhisper.codesearchengine.Interfaces;

import com.zerowhisper.codesearchengine.models.MFile;

import java.util.List;

public interface ParsingService {
    void processFile(List<MFile> files);
}
