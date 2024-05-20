package com.sbm.mc.reviewprohandler.repository;

import com.sbm.mc.reviewprohandler.domain.RvpApiGlobalReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RvpApiGlobalReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvpApiGlobalReviewRepository extends MongoRepository<RvpApiGlobalReview, Integer> {}
