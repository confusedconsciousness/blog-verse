package org.example.blogverse.repositories.implementation;

import com.google.inject.Inject;
import io.appform.dropwizard.sharding.dao.LookupDao;
import org.example.blogverse.models.Post;
import org.example.blogverse.repositories.PostRepository;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Optional;

public class PostRepositoryImplementation implements PostRepository {
    private final LookupDao<Post> postLookupDao;

    @Inject
    public PostRepositoryImplementation(LookupDao<Post> postLookupDao) {
        this.postLookupDao = postLookupDao;
    }

    @Override
    public Post save(Post post) throws Exception {
        return postLookupDao.save(post).get();
    }

    @Override
    public Optional<Post> getPost(String postId) throws Exception {
        return postLookupDao.get(postId);
    }

    @Override
    public List<Post> getPostForUser(String email) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(Post.class);
        criteria.add(Restrictions.eq("email", email));
        return postLookupDao.scatterGather(criteria);
    }

    @Override
    public List<Post> getAllPost() throws Exception {
        // here we have to write a detached criteria with no constraints
        DetachedCriteria criteria = DetachedCriteria.forClass(Post.class);
        return postLookupDao.scatterGather(criteria);
    }
}
