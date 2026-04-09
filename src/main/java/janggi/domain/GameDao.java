package janggi.domain;

import janggi.dto.GameDto;

import java.sql.*;
import java.util.Optional;

public class GameDao {

    public long save(GameDto gameDto) {
        String sql = "INSERT INTO game (game_status) VALUES (?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, gameDto.gameStatus());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new SQLException("게임 데이터 저장 중 오류가 발생했습니다.");
        } catch (SQLException e) {
            throw new RuntimeException("게임 데이터 저장 중 오류가 발생했습니다.", e);
        }
    }
}
