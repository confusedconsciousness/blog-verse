package org.example.blogverse.repositories.implementation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.appform.dropwizard.sharding.dao.LookupDao;
import org.example.blogverse.models.User;
import org.example.blogverse.repositories.UserRepository;

import java.util.Optional;

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
}
