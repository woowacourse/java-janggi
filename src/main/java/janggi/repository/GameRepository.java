package janggi.repository;

import janggi.domain.Game;
import janggi.dto.TurnDto;

import java.sql.Connection;

public interface GameRepository {
    long saveNewGame(Game game, Connection connection);

    Game enterGame(long roomId, Connection connection);

    void saveMove(long roomId, Game game, TurnDto turnDto, Connection connection);
}