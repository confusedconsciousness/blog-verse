package org.example.blogverse.repositories;

import org.example.blogverse.models.User;

import java.util.Optional;

public interface UserRepository {

    // this method will save the user to the database
    User save(User user) throws Exception;
    // this method will be used to fetch the user by the lookup key which is email
    // why optional? because it is possible that there is not user by that email
    Optional<User> getUser(String email) throws Exception;

}
