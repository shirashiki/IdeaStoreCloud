package com.github.shirashiki.ideastore.cloud.repository;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for a repository which can store Idea objects
 * @author silvio hirashiki
 *
 */
@Repository
public interface IdeaRepository extends CrudRepository<Idea, Long> {

	// Find Ideas with matching name
	public Collection<Idea> findByName(String name);
	
}
