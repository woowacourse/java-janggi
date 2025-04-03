package domain.dao;

import domain.piece.Team;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.DBConnectionUtil;

public class GameDaoImpl implements GameDao {

    @Override
    public Long add(String name, Team team) {
        final var query = "INSERT INTO game (name, turn) VALUES(?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, team.toString());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("게임을 생성하는데 실패했습니다.");
    }

    public Long findIdByName(final String name) {
        final var query = "SELECT * FROM game WHERE name = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("game_id");
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

    public Team findTurn(Long gameId) {
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

    @Override
    public void changeTurn(Long gameId, Team turn) {
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
