package janggi.db.dao;

import janggi.db.entity.JanggiGameEntity;
import java.sql.Connection;
import java.sql.SQLException;

public interface JanggiGameDao {

    int selectOngoingGameCount(Connection connection) throws SQLException;

    JanggiGameEntity selectOngoingGame(Connection connection) throws SQLException;

    int insertNewGame(Connection connection, String currentTurn) throws SQLException;

    void updatePlayingSideByGameId(Connection connection, int gameId, String currentTurn) throws SQLException;

    void deleteGameByGameId(Connection connection, int gameId) throws SQLException;
}
