package com.sparetimedevs.questlog.quest;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "quest", path = "quest")
public interface QuestRepository extends PagingAndSortingRepository<Quest, Long> {

	List<Quest> findByUserEmailAddress(@Param("USER_EMAIL_ADDRESS") String userEmailAddress);

}
