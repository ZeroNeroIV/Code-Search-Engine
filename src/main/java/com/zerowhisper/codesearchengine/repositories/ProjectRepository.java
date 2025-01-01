package com.zerowhisper.codesearchengine.repositories;

import com.zerowhisper.codesearchengine.models.MProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<MProject, Long> {
}
