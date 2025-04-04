package db;

import domain.GameState;
import domain.Team;
import java.sql.Connection;

public interface JanggiGameRepository {

    Team getTurn(final Connection connection);

    GameState getGameState(final Connection connection);

    void updateGameState(final Connection connection, final GameState gameState);

    void changeTurn(final Connection connection, final Team team);
}
