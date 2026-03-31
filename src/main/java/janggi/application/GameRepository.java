package janggi.application;

import janggi.application.dto.GameSnapshot;
import janggi.application.dto.GameSummary;
import java.util.List;
import java.util.Optional;

public interface GameRepository {
    List<GameSummary> findAll();
    Optional<GameSnapshot> findById(Long gameId);
    void save(GameSnapshot gameSnapshot);
    void update(GameSnapshot gameSnapshot);
}
