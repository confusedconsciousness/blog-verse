package org.example.blogverse.models;

import io.appform.dropwizard.sharding.sharding.LookupKey;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Transactional
@Entity
@Table(name = "user")
public class User {

    @Id
    @NonNull
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column
    private String name;

    @NonNull
    @Column
    @LookupKey
    private String email;

    @NonNull
    @Column
    private String password;

    @NonNull
    @Column
    private String token;

}
