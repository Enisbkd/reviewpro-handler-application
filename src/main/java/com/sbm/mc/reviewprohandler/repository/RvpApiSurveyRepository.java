package com.sbm.mc.reviewprohandler.repository;

import com.sbm.mc.reviewprohandler.domain.RvpApiSurvey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RvpApiSurvey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvpApiSurveyRepository extends MongoRepository<RvpApiSurvey, Integer> {}
