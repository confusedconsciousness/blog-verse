package org.example.blogverse.resources;

// This resource needs to be registered first to the BlogVerseApplication
// our endpoint will be localhost:8080/
// we need to define the path, from where our content will be accessible
// @Path("/blog") tells jersey that this resource is accessible at the URI/blog,
// @Produces(MediaType.APPLICATION_JSON) lets Jersey's content negotiation code know that this resource produces
// representations which are application/json

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import org.example.blogverse.models.Post;
import org.example.blogverse.models.User;
import org.example.blogverse.repositories.PostRepository;
import org.example.blogverse.repositories.UserRepository;
import org.example.blogverse.utils.AccessTokenUtil;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/v0/blog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogVerseResource {
    @Inject
    private UserRepository userRepository;

    @Inject
    private PostRepository postRepository;


    // *************************     Resources related to USER   ******************************


    @Path("/create-user")
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

    // *************************     Resources related to POSTS   ******************************

    @Path("/posts")
    @GET
    @Timed
    public Response getAllPosts() throws Exception {
        // shows all the post present
        return Response.ok(postRepository.getAllPost()).build();
    }

    @Path("/posts/email")
    @GET
    @Timed
    public Response getPostsForUser(
            @NotNull
            @NotEmpty
            @QueryParam("email") String email
    ) throws Exception{
        // shows the post specific to a user uniquely identified by email
        return Response.ok(postRepository.getPostForUser(email)).build();
    }

    @Path("/posts/postId")
    @GET
    @Timed
    public Response getPost(
            @NotNull
            @NotEmpty
            @QueryParam("postId") String postId
    ) throws Exception{
        // gives you post having postId that matches your postId
        return Response.ok(postRepository.getPost(postId)).build();
    }

    @Path("/create-post")
    @POST
    @Timed
    public Response createPost(
            @Valid Post post,
            @HeaderParam("Token") String token,
            @HeaderParam("Email") String email
    ) throws Exception {
        // creates a post
        // we need to make sure that the author that is posting is authenticated or authorised to do so
        Optional<User> user = userRepository.getUser(email);
        if(!user.isPresent()) {
            // if there is no such user present, we shall throw 403 error
            return Response.status(403).build();
        }
        // if the user is present, let's see if the token is valid
        if(isAuthenticated(token, email)) {
            // generate a random postID to associate the post with
            post.setPostId(UUID.randomUUID().toString());
            return Response.ok(postRepository.save(post)).build();
        }
        return Response.status(403).build();
    }



    public boolean isAuthenticated(String token, String email) throws Exception {
        // this function basically authenticates the user by combining two functions
        // isTokenValid and isAuthorised
        return  userRepository.isAuthorised(token, email) && AccessTokenUtil.isTokenValid(token);
    }

}
