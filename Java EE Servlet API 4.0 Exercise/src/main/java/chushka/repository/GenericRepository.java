package chushka.repository;

import java.util.List;

public interface GenericRepository<E, K> {
    E save(E entity);

    E findByName(K id);

    List<E> findAll();
}
