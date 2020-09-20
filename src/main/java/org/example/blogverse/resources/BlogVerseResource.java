package org.example.blogverse.resources;

// This resource needs to be registered first to the BlogVerseApplication
// our endpoint will be localhost:8080/
// we need to define the path, from where our content will be accessible
// @Path("/blog") tells jersey that this resource is accessible at the URI/blog,
// @Produces(MediaType.APPLICATION_JSON) lets Jersey's content negotiation code know that this resource produces
// representations which are application/json

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import org.example.blogverse.models.User;
import org.example.blogverse.repositories.UserRepository;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/blog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogVerseResource {
    @Inject
    private UserRepository userRepository;

    @Path("/createUser")
    @POST
    @Timed
    public Response createUser(@Valid User user) throws Exception {
        /*
        This function is mounted at localhost:8080/blog/createUser
        it saves the data of the user that is passed in the request body to the MariaDB database (locally)
        you need to set up the database, the instructions are given in the Readme.md
         */
        return Response.ok(userRepository.save(user)).build();
    }

}
