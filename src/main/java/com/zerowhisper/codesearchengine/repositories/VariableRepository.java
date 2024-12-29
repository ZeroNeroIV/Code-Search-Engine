package com.zerowhisper.codesearchengine.repositories;

import com.zerowhisper.codesearchengine.models.MVariable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariableRepository extends JpaRepository<MVariable,Long> {
}
