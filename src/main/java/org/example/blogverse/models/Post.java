package org.example.blogverse.models;

import io.appform.dropwizard.sharding.sharding.LookupKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.transaction.Transactional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Entity
@Table(name = "post")
public class Post {
    @Id
    @NonNull
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column
    @LookupKey
    private String postId;

    @NonNull
    @Column
    private String title;

    @NonNull
    @Column
    private String subtitle;

    @NonNull
    @Column
    private String content;

    @NonNull
    @Column
    private String author;

    @NonNull
    @Column
    private String email;

    @NonNull
    @Column
    private String createdAt;

}
