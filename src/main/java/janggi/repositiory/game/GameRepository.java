package janggi.repositiory.game;

import janggi.domain.piece.Team;

import java.util.Optional;

public interface GameRepository {
    Long save(Boolean isFinished, Team currentTurn);
    Optional<GameData> findLatestGame();
    void update(Long id, boolean isFinished, Team currentTurn);
}
