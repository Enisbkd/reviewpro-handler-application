package com.sbm.mc.reviewprohandler.repository;

import com.sbm.mc.reviewprohandler.domain.RvpApiResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RvpApiResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvpApiResponseRepository extends MongoRepository<RvpApiResponse, Integer> {}
