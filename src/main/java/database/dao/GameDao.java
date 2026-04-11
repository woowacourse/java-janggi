package database.dao;

import database.dto.GameDto;
import domain.game.Team;
import java.util.Optional;

public interface GameDao {
    int createGame(Team initialTurn);

    Optional<GameDto> findLatestPlaying();

    void updateTurn(int gameId, Team team);

    void deleteById(int id);
}
