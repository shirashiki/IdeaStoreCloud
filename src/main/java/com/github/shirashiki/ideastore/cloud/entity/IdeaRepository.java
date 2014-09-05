package com.github.shirashiki.ideastore.cloud.entity;

import org.springframework.data.repository.CrudRepository;
/**
 * An interface for  ideas
 * @author silviohirashiki
 *
 */
public interface IdeaRepository extends CrudRepository<Idea, Long> {

}
