package org.example.blogverse.repositories;

import org.example.blogverse.models.User;

import java.util.Optional;
import java.util.function.Function;

public interface UserRepository {

    // this method will save the user to the database
    User save(User user) throws Exception;
    // this method will be used to fetch the user by the lookup key which is email
    // why optional? because it is possible that there is not user by that email
    Optional<User> getUser(String email) throws Exception;
    // let's add whether the user is authorised?
    boolean isAuthorised(String token, String email) throws Exception;
    // a function that will be used to update the column (token)
    boolean update(String email, Function<Optional<User>, User> updater);
}
