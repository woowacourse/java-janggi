package janggi.repositiory.game;

import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.piece.Team;
import janggi.domain.vo.FinishStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface GameRepository {
    Long save(Connection conn, FinishStatus status, Team turn) throws SQLException;
    Optional<GameData> findLatestGame(Connection conn);
    void updateStatus(Connection conn, Long id, JanggiGame game);
}
