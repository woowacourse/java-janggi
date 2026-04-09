package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameDao {

    public long create(Connection connection, String turn) throws SQLException {

        String sql = "INSERT INTO game (turn) VALUES (?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        {
            preparedStatement.setString(1, turn);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
            throw new SQLException("ID 생성 실패");
        }
    }

    public void updateTurn(Connection connection, long gameId, String turn) throws SQLException {
        String sql = "UPDATE game SET turn = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, turn);
        preparedStatement.setLong(2, gameId);
        preparedStatement.executeUpdate();

    }

    public String findTurn(Connection connection, long gameId) throws SQLException {
        String sql = "SELECT turn FROM game WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1, gameId);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("turn");
            }
        }
        throw new IllegalArgumentException("존재하지 않는 게임 방입니다.");
    }


    public Map<Long, String> findAll(Connection connection) throws SQLException {
        String sql = "SELECT id, turn FROM game ORDER BY id DESC";

        Map<Long, String> games = new LinkedHashMap<>();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            games.put(resultSet.getLong("id"), resultSet.getString("turn"));
        }
        return games;
    }
}
