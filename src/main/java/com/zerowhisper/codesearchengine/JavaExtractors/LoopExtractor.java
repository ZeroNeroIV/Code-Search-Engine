package com.zerowhisper.codesearchengine.JavaExtractors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForStmt;
import com.zerowhisper.codesearchengine.models.MLoop;
import com.zerowhisper.codesearchengine.models.MMethod;
import com.zerowhisper.codesearchengine.repositories.LoopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoopExtractor{

    private final LoopRepository loopRepository;

    @Autowired
    public LoopExtractor(LoopRepository loopRepository) {
        this.loopRepository = loopRepository;
    }

    public void process(MMethod mMethod, MethodDeclaration methodDeclaration) {

             methodDeclaration.findAll(ForStmt.class).forEach(lop -> {
                 MLoop loop = new MLoop();
                 loop.setMethod(mMethod);
                 if (lop.isForStmt()) {
                     loop.setLoopType("Loop-for");
                 } else if (lop.isWhileStmt()) {
                     loop.setLoopType("Loop-While");
                 } else if (lop.isDoStmt()) {
                     loop.setLoopType("Loop-DoWhile");
                 }

                lop.getInitialization().forEach(statement -> {
                             loop.setInitialization( statement.toString());});

                 lop.getCompare().ifPresent(statement -> {
                     loop.setCondition( statement.toString());});

                 List<String>updates=new ArrayList<>();
                 lop.getUpdate().forEach(statement -> {
                     updates.add(statement.toString());
                 });

                 loop.setUpdates(updates);

                 lop.getRange().ifPresent(range -> {
                     // 1. Make a Map<String, Object>
                     Map<String, Object> map = new HashMap<>();
                     // 2. put the entries into this map
                     map.put("StartRow", range.begin.line);
                     map.put("EndRow", range.end.line);

                     map.put("StartColumn", range.begin.column);
                     map.put("EndColumn", range.end.column);

                     // 3. use toString() then you are done
                     ObjectMapper mapper = new ObjectMapper();
                     JsonNode jsonNode = mapper.valueToTree(map);
                     loop.setPosition(jsonNode);
                 });

                loopRepository.save(loop);
             });

    }
}
