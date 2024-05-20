package com.sbm.mc.reviewprohandler.repository;

import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingCqi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RvpApiLodgingCqi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvpApiLodgingCqiRepository extends MongoRepository<RvpApiLodgingCqi, Integer> {}
