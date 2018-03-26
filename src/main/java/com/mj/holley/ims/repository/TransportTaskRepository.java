package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.TransportTask;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransportTask entity.
 */
@SuppressWarnings("unused")
public interface TransportTaskRepository extends JpaRepository<TransportTask,Long> {

}
