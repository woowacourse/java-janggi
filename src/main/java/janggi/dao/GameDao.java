package janggi.dao;

import janggi.dto.GameSummary;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class GameDao {

    private static final String SERVER = "localhost:3306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "11111111"; // MySQL 서버 비밀번호

    private final String url;

    public GameDao() {
        url = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;
    }

    public GameDao(final String database) {
        url = "jdbc:mysql://" + SERVER + "/" + database + OPTION;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<GameSummary> getAllGames() {
        final String query = "SELECT id, turn, created_at FROM game ORDER BY created_at";
        final List<GameSummary> games = new ArrayList<>();

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final int id = resultSet.getInt("id");
                final String turn = resultSet.getString("turn");
                final Timestamp createdAt = resultSet.getTimestamp("created_at");
                games.add(new GameSummary(id, turn, createdAt.toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return games;
    }
}
