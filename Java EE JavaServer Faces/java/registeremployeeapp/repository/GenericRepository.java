package registeremployeeapp.repository;

import java.util.List;

public interface GenericRepository<E, K> {
    E save(E entity);

    List<E> findAll();

    E findById(K id);

    void removeById(K id);
}
