package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.Sn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Sn entity.
 */
@SuppressWarnings("unused")
@Transactional
public interface SnRepository extends JpaRepository<Sn,Long> {

    //根据序列号、工装板号更新绑定状态
    @Modifying
    @Query(value = "update Sn s set s.isBinding = ?1 ,s.unbundlingTime = ?2 where s.hutID = ?3 and s.serialNumber = ?4  ")
    int updateIsBindingFalse(Boolean isBinding , ZonedDateTime unbundlingTime , String hutID , String serialNumber);

    //根据序列号、工装板号查找
    List<Sn> findByHutIDAndSerialNumberAndIsBindingTrue(String hutID , String serialNumber);
    Optional<Sn> findFirstByHutIDAndIsBindingTrueOrderByIdDesc(String hutID);
}
