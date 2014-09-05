package com.github.shirashiki.ideastore.client;

import java.util.Collection;

import com.github.shirashiki.ideastore.cloud.entity.Idea;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * This interface defines an API for a Idea Service. The
 * interface is used to provide a contract for client/server
 * interactions. The interface is annotated with Retrofit
 * annotations so that clients can automatically convert the
 * data from JSON to objects
 * 
 * @author Silvio Hirashiki
 *
 */
public interface IdeaSvcApi {

	public static final String NAME_PARAMETER = "name";

	// The path where we expect the VideoSvc to live
	public static final String IDEA_SVC_PATH = "/idea";

	// The path to search videos by title
	public static final String IDEA_NAME_SEARCH_PATH = IDEA_SVC_PATH + "/find";

	@GET(IDEA_SVC_PATH)
	public Collection<Idea> getIdeaList();
	
	@POST(IDEA_SVC_PATH)
	public boolean addIdea(@Body Idea ideaToAdd);
	
	@GET(IDEA_NAME_SEARCH_PATH)
	public Collection<Idea> findByName(@Query(NAME_PARAMETER) String name);
	
	
}
