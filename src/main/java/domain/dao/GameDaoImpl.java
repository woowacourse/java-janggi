package domain.dao;

import domain.piece.Team;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBConnectionUtil;

public class GameDaoImpl implements GameDao {

    private final Long gameId;

    public GameDaoImpl(Long gameId) {
        this.gameId = gameId;
    }

    public Team findTurn() {
        final var query = "SELECT * FROM game WHERE game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.find(resultSet.getString("turn"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("게임이 존재하지 않습니다.");
    }

    public void changeTurn(final Team turn) {
        final var query = "UPDATE game SET turn = ? WHERE game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, turn.toString());
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.createConnection();
    }
}
