package janggi.dao;

import janggi.domain.game.Team;
import janggi.dto.GameDto;
import java.sql.Connection;
import java.util.List;

public interface GameDao {

    List<GameDto> findAllGames(final Connection connection);

    GameDto findGameById(final Connection connection, final int gameId);

    int addGame(final Connection connection, final Team turn);

    void updateGameById(final Connection connection, final int gameId, final Team turn);

    void deleteGameById(final Connection connection, final int id);
}
