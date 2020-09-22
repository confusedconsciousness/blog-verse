package org.example.blogverse.repositories;

import org.example.blogverse.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    // to save the post in the database
    Post save(Post post) throws Exception;

    // to get a certain post based on the postId
    Optional<Post> getPost(String postId) throws Exception;

    // return list of post written by a user
    List<Post> getPostForUser(String email) throws Exception;
    // will retrieve all the post till date
    List<Post> getAllPost() throws Exception;
}
