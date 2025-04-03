package janggi.repository;

import janggi.GameId;
import janggi.GameStatus;
import janggi.player.Score;
import janggi.player.Turn;
import janggi.repository.dto.GameDto;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    GameId save(Connection connection, Turn turn, Score choScore, Score hanScore);

    GameId save(Connection connection, GameId gameId, Turn turn, Score choScore, Score hanScore);

    Optional<GameDto> findById(Connection connection, GameId id);

    List<GameDto> findAllRunning(Connection connection);

    void updateStatusById(Connection connection, GameId id, GameStatus status);
}


