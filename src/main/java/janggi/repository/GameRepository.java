package janggi.repository;

import janggi.dto.GameSnapshot;
import janggi.dto.GameSummary;
import java.util.List;
import java.util.Optional;

public interface GameRepository {
    List<GameSummary> findAll();
    Optional<GameSnapshot> findById(Long gameId);
    Long save(GameSnapshot gameSnapshot);
    void update(GameSnapshot gameSnapshot);
}
