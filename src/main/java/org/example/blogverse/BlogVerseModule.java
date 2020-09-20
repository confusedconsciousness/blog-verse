package org.example.blogverse;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.dao.LookupDao;
import io.appform.dropwizard.sharding.dao.RelationalDao;
import org.example.blogverse.models.User;
import org.example.blogverse.repositories.UserRepository;
import org.example.blogverse.repositories.implementation.UserRepositoryImplementation;

public class BlogVerseModule extends AbstractModule {
    private final DBShardingBundle<BlogVerseConfiguration> dbShardingBundle;

    public BlogVerseModule(DBShardingBundle<BlogVerseConfiguration> dbShardingBundle) {
        this.dbShardingBundle = dbShardingBundle;
    }

    @Override
    protected void configure() {
        // binds the repository to the implementation
        bind(UserRepository.class).to(UserRepositoryImplementation.class);
    }

    // for User
    @Provides
    @Singleton
    public LookupDao<User> provideUserConfigurationLookUpDao() {
        return dbShardingBundle.createParentObjectDao(User.class);
    }

    @Provides
    @Singleton
    public RelationalDao<User> providesUserDao() {
        return dbShardingBundle.createRelatedObjectDao(User.class);
    }

}
