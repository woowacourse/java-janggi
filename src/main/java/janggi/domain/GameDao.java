package janggi.domain;

import janggi.dto.GameDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static janggi.domain.GameStatus.IN_PROGRESS;

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

    public List<GameDto> findInProgressGames() {
        String sql = "SELECT id, game_status FROM game WHERE game_status = (?)";
        List<GameDto> games = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, IN_PROGRESS.getFormat());
            ;

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    games.add(new GameDto(
                            rs.getLong("id"),
                            rs.getString("game_status")
                    ));
                }
            }
            return games;
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임 목록 추출 중 오류 발생", e);
        }
    }
}
