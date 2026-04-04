package janggi.repository;

import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import janggi.repository.dto.LatestInProgressGameResponse;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

public interface gameRepository {
    Optional<LatestInProgressGameResponse> findLatestInProgressGame(Connection con);

    Long saveBoard(Connection con, String team, Map<Position, Piece> boardInfo);

    void updateBoardWith(Connection con, Position from, Position to);

    void deleteByGameId(Connection con, Long gameId);
}
