package com.sbm.mc.reviewprohandler.repository;

import com.sbm.mc.reviewprohandler.domain.RvpApilodging;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RvpApilodging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvpApilodgingRepository extends MongoRepository<RvpApilodging, String> {}
