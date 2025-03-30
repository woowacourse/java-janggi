package janggi.dao;

import janggi.domain.game.Team;
import janggi.dto.GameDto;
import java.util.List;

public interface GameDao {

    List<GameDto> findAllGames();

    GameDto findGameById(final int gameId);

    int addGame(final Team turn);

    void updateGameById(final int gameId, final Team turn);

    void deleteGameById(final int id);
}
