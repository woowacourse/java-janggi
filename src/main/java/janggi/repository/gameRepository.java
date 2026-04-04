package janggi.repository;

import janggi.model.Team;
import janggi.model.position.absolute.Position;
import janggi.repository.dto.LatestInProgressGameResponse;
import java.sql.Connection;
import java.util.Optional;

public interface gameRepository {
    Optional<LatestInProgressGameResponse> findLatestInProgressGame(Connection con);

    Long save(Connection con, Team team);

    void updateBoardWith(Connection con, Position from, Position to);

    void deleteByGameId(Connection con, Long gameId);
}
