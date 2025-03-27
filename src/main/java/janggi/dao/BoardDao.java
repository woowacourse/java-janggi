package janggi.dao;

import janggi.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class BoardDao {
    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public void createBoard() {
        final String query = "INSERT INTO Board () VALUES ()";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("보드 생성 실패", e);
        }
    }

    public Optional<Integer> findRecentlyBoardId() {
        final String query = "SELECT id FROM Board ORDER BY id DESC LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return Optional.of(resultSet.getInt(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("가장 최근 Board Id 찾기 실패", e);
        }
    }

}
