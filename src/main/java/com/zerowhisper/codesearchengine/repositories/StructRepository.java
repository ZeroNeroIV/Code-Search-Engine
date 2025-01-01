package com.zerowhisper.codesearchengine.repositories;

import com.zerowhisper.codesearchengine.models.MStruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructRepository extends JpaRepository<MStruct, Integer> {
}
