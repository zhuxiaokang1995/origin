package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.ArriveStationInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ArriveStationInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArriveStationInfoRepository extends JpaRepository<ArriveStationInfo, Long> {
    //根据serialNumber查找进站记录
    ArriveStationInfo findBySerialNumber(String serialNumber);
}
