package com.redhat.developers;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse.Status;

import java.util.List;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@Path("messages")
public class GreetingResource {

    @Inject
    Logger LOGGER;

    @RestClient
    HelloService helloService;

    public static final String URI = "uri";
    public static final String API_GREET = "api.greet";

    @Path("/init")
    @GET
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Response init() {
        LOGGER.debug("Updating the db from external service");
        List<Message> messages = Message.findAll().list();
        for (Message message : messages) {
            String language = message.language;
            message.update(helloService.getContent(language).hello, language);
        }
        LOGGER.debug("End update of the db ");

        return Response.status(Status.CREATED).entity("DB initialized").build();

    }

    @POST
    @Transactional
    @Timed(value = "greetings.creation", longTask = true, extraTags = {URI, API_GREET})
    public Message create(Message message) {
         Message.persist(message);
         return message;
    }

    @GET
    @Path("/sysresources")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSystemResources() {
        long memory = Runtime.getRuntime().maxMemory();
        int cores = Runtime.getRuntime().availableProcessors();
        return " Memory: " + (memory / 1024 / 1024) +
                " Cores: " + cores + "\n";
    }

    @GET
    @Counted(value = "http.get.requests", extraTags = {URI, API_GREET})
    public List<Message> findAll() {
        return Message.findAll().list();
    }
    

}