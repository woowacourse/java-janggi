package db.repository;

import core.GameStatus;
import core.GameSummary;
import core.JanggiGame;
import java.util.List;
import java.util.Optional;
import participant.Turn;
import position.Position;

public interface JanggiGameRepository {

    Long save(JanggiGame game);

    Optional<JanggiGame> findById(Long gameId);

    List<GameSummary> findTop10GameRoomsOrderByCreatedAtDesc();

    void updateGameState(Long gameId, Turn turn, GameStatus status);

    void updatePiecePosition(Long gameId, Position departure, Position destination);
}
