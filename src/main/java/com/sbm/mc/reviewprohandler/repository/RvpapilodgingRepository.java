package com.sbm.mc.reviewprohandler.repository;

import com.sbm.mc.reviewprohandler.domain.Rvpapilodging;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Rvpapilodging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvpapilodgingRepository extends MongoRepository<Rvpapilodging, String> {}
