package com.zerowhisper.codesearchengine.repositories;

import com.zerowhisper.codesearchengine.models.MMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MethodRepository extends JpaRepository<MMethod,Long> {
}
