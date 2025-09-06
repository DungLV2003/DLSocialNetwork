package com.dunglv.profile.repository;

import com.dunglv.profile.entity.UserProfile;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserProfileRepository extends Neo4jRepository<UserProfile, String> {
    Optional<UserProfile> findByUserId(String userId);
    List<UserProfile> findAllByUsernameLike(String username);


}
