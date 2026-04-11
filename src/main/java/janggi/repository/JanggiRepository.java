package janggi.repository;

import janggi.domain.Janggi;

import java.util.Map;
import java.util.Optional;

public interface JanggiRepository {
    
    Long save(Janggi janggi);
    void update(Long id, Janggi janggi);
    Optional<Janggi> findById(Long id);
    Map<Long, Janggi> findAll();
    void delete(Long id);
}
