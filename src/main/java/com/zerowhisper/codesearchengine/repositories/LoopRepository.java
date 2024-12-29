package com.zerowhisper.codesearchengine.repositories;

import com.zerowhisper.codesearchengine.models.MLoop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoopRepository extends JpaRepository<MLoop,Long> {
}
