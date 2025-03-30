package domain.dao;

import domain.JanggiBoard;
import domain.JanggiGame;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.DBConnectionUtil;

public class GamesDaoImpl implements GamesDao {

    public JanggiGame add(final String name, final Team team) {
        final var query = "INSERT INTO game (name, turn) VALUES(?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, team.toString());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long gameId = generatedKeys.getLong(1);
                return new JanggiGame(new GameDaoImpl(gameId), JanggiBoard.init(new PieceDaoImpl(gameId)));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("게임을 생성하는데 실패했습니다.");
    }

    public JanggiGame findByName(final String name) {
        final var query = "SELECT * FROM game WHERE name = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long gameId = resultSet.getLong("game_id");
                return new JanggiGame(new GameDaoImpl(gameId), JanggiBoard.of(new PieceDaoImpl(gameId)));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("게임이 존재하지 않습니다.");
    }

    public List<String> findAllName() {
        final var query = "SELECT * FROM game";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> games = new ArrayList<>();
            while (resultSet.next()) {
                games.add(resultSet.getString("name"));
            }
            return games;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long countAll() {
        final var query = "SELECT COUNT(*) AS row_count FROM game";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("row_count");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("조회에 실패했습니다.");
    }

    private Connection getConnection() {
        return DBConnectionUtil.createConnection();
    }
}
