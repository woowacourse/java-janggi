package repository;

import java.util.List;

public interface Repository<E> {
    void create(final E E);

    void save(final E e);

    E findById(int id);

    List<E> findAllByKey(int key);
}
