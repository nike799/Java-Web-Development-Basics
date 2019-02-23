package casebook.repository.user;

import casebook.domain.entities.User;
import casebook.repository.BaseRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl extends BaseRepository implements UserRepository {

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public User findByUsername(String username) {
        return this.executeTransaction(entityManager ->
                entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                        .setParameter("username", username).getSingleResult()
        );
    }

    @Override
    public User save(User entity) {
        return this.executeTransaction(entityManager -> {
            entityManager.persist(entity);
            return entity;
        });

    }

    @Override
    public User update(User entity) {
        return this.executeTransaction(entityManager -> entityManager.merge(entity));
    }

    @Override
    public List<User> findAll() {
        return this.executeTransaction(entityManager ->
                entityManager.createQuery("SELECT u FROM User u", User.class).getResultList());
    }

    @Override
    public User findById(String id) {
        return this.executeTransaction(entityManager ->
                entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                        .setParameter("id", id).getSingleResult());
    }

    @Override
    public Long size() {
        return this.executeTransaction(entityManager ->
                entityManager.createQuery("SELECT count(u) FROM User u", Long.class).getSingleResult());
    }
}
