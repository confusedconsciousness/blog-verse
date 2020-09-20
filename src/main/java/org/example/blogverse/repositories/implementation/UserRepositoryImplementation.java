package org.example.blogverse.repositories.implementation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.appform.dropwizard.sharding.dao.LookupDao;
import org.example.blogverse.models.User;
import org.example.blogverse.repositories.UserRepository;

import java.util.Optional;
import java.util.function.Function;

@Singleton
public class UserRepositoryImplementation implements UserRepository {
    // we will be needing lookupDao to communicate to the database
    private final LookupDao<User> userLookupDao;

    @Inject
    public UserRepositoryImplementation(LookupDao<User> userLookupDao) {
        this.userLookupDao = userLookupDao;
    }

    @Override
    public User save(User user) throws Exception {
        Optional<User> userToSave;
        // we will use try and catch block, you can use it or not it's your choice
        try {
            userToSave = userLookupDao.save(user);
            return userToSave.get();
        } catch (Exception e) {
            System.out.println("\n\n\n Adding to DB failed");
            throw e;
        }
//        return userLookupDao.save(user);
    }

    @Override
    public Optional<User> getUser(String email) throws Exception {
        return userLookupDao.get(email);
    }

    @Override
    public boolean update(String email, Function<Optional<User>, User> updater) {
        return userLookupDao.update(email, updater);
    }

    @Override
    public boolean isAuthorised(String token, String email) throws Exception {
        // first we will fetch the user using the email provided to us
        Optional<User> user = userLookupDao.get(email);
        // let's check whether there is such user or not
        if(!user.isPresent()) {
            // no such user is present, so of course he is not authorised as well
            return false;
        } else {
            // if he is present, check whether the token that he came up with is still valid
            if(user.get().getToken().equals(token)) {
                // if token matches, it means he is authorised
                return true;
            }
        }
        return false;
    }


}
