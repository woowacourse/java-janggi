package dao;

import java.util.Optional;

public interface TransactionManager<T> {

    void createTable();

    void create(T t);

    void update(Long id, T t);

    Optional<T> findById(Long id);
}
