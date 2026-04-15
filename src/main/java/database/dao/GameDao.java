package database.dao;

import database.dto.GameDto;
import domain.game.Team;
import java.sql.Connection;
import java.util.Optional;

public interface GameDao {
    int createGame(Team initialTurn);

    Optional<GameDto> findLatestPlaying();

    void updateTurn(Connection connection, int gameId, Team team);

    void deleteById(int id);
}
