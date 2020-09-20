package org.example.blogverse;

import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogVerseConfiguration extends Configuration {
    @NotNull
    @Valid
    private ShardedHibernateFactory database; // notice that database here and database present in the config.yml
}
