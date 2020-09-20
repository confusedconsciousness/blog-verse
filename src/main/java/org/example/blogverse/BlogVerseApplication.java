package org.example.blogverse;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class BlogVerseApplication extends Application<BlogVerseConfiguration> {

    public static void main(String[] args) throws Exception{
        BlogVerseApplication application = new BlogVerseApplication();
        application.run(args);
    }

    @Override
    public void run(BlogVerseConfiguration blogVerseConfiguration, Environment environment) throws Exception {

    }
}
