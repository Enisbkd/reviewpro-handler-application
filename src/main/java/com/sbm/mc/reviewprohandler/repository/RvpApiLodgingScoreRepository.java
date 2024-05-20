package com.sbm.mc.reviewprohandler.repository;

import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingScore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RvpApiLodgingScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvpApiLodgingScoreRepository extends MongoRepository<RvpApiLodgingScore, Integer> {}
