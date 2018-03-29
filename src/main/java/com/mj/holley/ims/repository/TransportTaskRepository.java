package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.TransportTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;

/**
 * Spring Data JPA repository for the TransportTask entity.
 */
@SuppressWarnings("unused")
public interface TransportTaskRepository extends JpaRepository<TransportTask,Long> {

    TransportTask findByTaskID(Integer taskId);

    @Modifying
    @Query(value = "update transport_task t set t.fun_id = ?1 , t.serial_id = ?2 , t.task_type = ?3 , t.task_prty = ?4 ,\n" +
        "t.task_flag = ?5 , t.l_pn = ?6 , t.frm_pos = ?7 , t.frm_pos_type = ?8 , t.to_pos = ?9 , t.to_pos_type = ?10 ,\n" +
        "t.op_flag = ?11 , t.remark = ?12 , t.issued_task_time = ?13  where t.task_id = ?14", nativeQuery = true)
    int updateTransportTask(String funId , Integer serialId , String taskType , String taskPrty , String taskFlag , String lpn ,
                            String frmPos , String frmPosType , String toPos , String toPosType , String opFlag , String remark ,
                            ZonedDateTime issuedTaskTime , Integer taskId);

}
