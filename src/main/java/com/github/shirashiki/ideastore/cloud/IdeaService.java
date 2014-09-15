package com.github.shirashiki.ideastore.cloud;


import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.shirashiki.ideastore.client.IdeaSvcApi;
import com.github.shirashiki.ideastore.cloud.repository.Idea;
import com.github.shirashiki.ideastore.cloud.repository.IdeaRepository;
import com.google.common.collect.Lists;

/**
 * Controller for Ideas
 * @author silvio hirashiki
 *
 */
@Controller
public class IdeaService {
	
	@Autowired
	private IdeaRepository ideas;

	@RequestMapping(value=IdeaSvcApi.IDEA_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addIdea(@RequestBody Idea newIdea){
		 ideas.save(newIdea);
		 return true;
	}
	
	
	
	/**
	 * GET /idea
	 * 
	 * Receives GET requests to /idea and returns the current
	 * list of ideas in the database. Spring automatically converts
	 * the list of ideas to JSON because of the @ResponseBody
	 * annotation
	 */
	@RequestMapping(value=IdeaSvcApi.IDEA_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Idea> getVideoList(){
		return Lists.newArrayList(ideas.findAll());
	}
	
}
