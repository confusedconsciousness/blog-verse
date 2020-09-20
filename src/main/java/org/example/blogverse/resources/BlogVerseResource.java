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
import org.example.blogverse.utils.AccessTokenUtil;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

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
        String token = AccessTokenUtil.createAccessToken();
        user.setToken(token);
        return Response.ok(userRepository.save(user)).build();
    }

    @Path("/login")
    @POST
    @Timed
    public Response loginUser(@Valid User user) throws Exception {
        // first let's check whether the user is present or not?
        Optional<User> userOptional = userRepository.getUser(user.getEmail());
        if(!userOptional.isPresent()) {
            return Response.status(402).build();
        }
        // if he is present, let's match the password shall we?
        User userDB = userOptional.get(); // this will give the user which is in database corresponding to the email provided by the visitor
        if(userDB.getPassword().equals(user.getPassword())) {
            // if password matches, we need to update the token of this man
            String newToken = AccessTokenUtil.createAccessToken();
            userRepository.update(user.getEmail(), updater -> {
                User updatedUser = updater.get();
                updatedUser.setToken(newToken);
                return updatedUser;
            });

            userDB.setToken(newToken);
            return Response.ok(userDB).build();
        }
        return Response.status(403).build();
    }


    public boolean isAuthenticated(String token, String email) throws Exception {
        // this function basically authenticates the user by combining two functions
        // isTokenValid and isAuthorised
        return  userRepository.isAuthorised(token, email) && AccessTokenUtil.isTokenValid(token);
    }

}
