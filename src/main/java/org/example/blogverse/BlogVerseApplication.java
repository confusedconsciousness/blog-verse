package org.example.blogverse;

import com.google.inject.Stage;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.blogverse.models.Post;
import org.example.blogverse.models.User;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class BlogVerseApplication extends Application<BlogVerseConfiguration> {

    private DBShardingBundle<BlogVerseConfiguration> dbShardingBundle;
    private GuiceBundle guiceBundle;

    public static void main(String[] args) throws Exception{
        BlogVerseApplication application = new BlogVerseApplication();
        application.run(args);
    }

    @Override
    public void initialize(Bootstrap<BlogVerseConfiguration> bootstrap) {
        // here we will initialize our bundles
        this.dbShardingBundle = new DBShardingBundle<BlogVerseConfiguration>(
                User.class, Post.class
        ) {
            @Override
            protected ShardedHibernateFactory getConfig(BlogVerseConfiguration blogVerseConfiguration) {
                return blogVerseConfiguration.getDatabase();
            }
        };

        bootstrap.addBundle(dbShardingBundle);
        guiceBundle = initGuiceBundle(dbShardingBundle);
        bootstrap.addBundle(guiceBundle);

    }

    private GuiceBundle initGuiceBundle(DBShardingBundle<BlogVerseConfiguration> dbShardingBundle) {
        return GuiceBundle.<BlogVerseConfiguration>builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(new BlogVerseModule(dbShardingBundle))
                .build(Stage.PRODUCTION);
    }

    @Override
    public void run(BlogVerseConfiguration blogVerseConfiguration, Environment environment) throws Exception {

    }
}
