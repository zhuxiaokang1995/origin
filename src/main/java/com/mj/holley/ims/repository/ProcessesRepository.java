package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.Processes;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Processes entity.
 */
@SuppressWarnings("unused")
public interface ProcessesRepository extends JpaRepository<Processes,Long> {

}
