package com.variomex.variomex.repository;

import com.variomex.variomex.model.GenomeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenomeFileRepository extends JpaRepository<GenomeFile, Long> {
}
