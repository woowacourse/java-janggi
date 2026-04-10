package janggi.repositiory.game;

import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.piece.Team;
import janggi.domain.vo.FinishStatus;

import java.util.Optional;

public interface GameRepository {
    Long save(FinishStatus finishStatus, Team currentTurn);
    Optional<GameData> findLatestGame();
    void update(Long id, JanggiGame game);
}
