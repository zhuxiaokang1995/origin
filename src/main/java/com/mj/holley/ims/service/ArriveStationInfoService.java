package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.ArriveStationInfo;
import com.mj.holley.ims.repository.ArriveStationInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing ArriveStationInfo.
 */
@Service
@Transactional
public class ArriveStationInfoService {

    private final Logger log = LoggerFactory.getLogger(ArriveStationInfoService.class);

    private final ArriveStationInfoRepository arriveStationInfoRepository;

    public ArriveStationInfoService(ArriveStationInfoRepository arriveStationInfoRepository) {
        this.arriveStationInfoRepository = arriveStationInfoRepository;
    }

    /**
     * Save a arriveStationInfo.
     *
     * @param arriveStationInfo the entity to save
     * @return the persisted entity
     */
    public ArriveStationInfo save(ArriveStationInfo arriveStationInfo) {
        log.debug("Request to save ArriveStationInfo : {}", arriveStationInfo);        return arriveStationInfoRepository.save(arriveStationInfo);
    }

    /**
     * Get all the arriveStationInfos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ArriveStationInfo> findAll() {
        log.debug("Request to get all ArriveStationInfos");
        return arriveStationInfoRepository.findAll();
    }


    /**
     * Get one arriveStationInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ArriveStationInfo> findOne(Long id) {
        log.debug("Request to get ArriveStationInfo : {}", id);
        return arriveStationInfoRepository.findById(id);
    }

    /**
     * Delete the arriveStationInfo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ArriveStationInfo : {}", id);
        arriveStationInfoRepository.deleteById(id);
    }
}
