package dao;

import java.util.Optional;

public interface TransactionManager<T> {

    void createTable();

    void create(T t);

    void update(T t);

    Optional<T> find();
}
