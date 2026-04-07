package db.repository;

import core.GameStatus;
import core.GameSummary;
import core.JanggiGame;
import java.util.List;
import java.util.Optional;
import participant.Turn;
import position.Position;

public interface JanggiGameRepository {

    Long save(final JanggiGame game);

    Optional<JanggiGame> findById(final Long gameId);

    List<GameSummary> findTop10GameRoomsOrderByCreatedAtDesc();

    void updateGameState(final Long gameId, final Turn turn, final GameStatus status);

    void updatePiecePosition(final Long gameId, final Position departure, final Position destination);
}
