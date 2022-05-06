package com.redhat.developers;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.inject.Singleton;

@RegisterRestClient
@Path("/hellosalut")
@Singleton
public interface HelloService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    ExternalGreeting getContent(@QueryParam("lang") String lang);
}