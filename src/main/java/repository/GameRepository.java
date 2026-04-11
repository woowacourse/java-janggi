package repository;

import domain.Janggi;
import java.util.Optional;

public interface GameRepository {
    void save(Janggi game);
    Optional<Janggi> findById(Long id);
    void delete(Janggi game);
}
