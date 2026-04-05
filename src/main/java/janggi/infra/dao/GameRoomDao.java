package janggi.infra.dao;

import janggi.infra.dto.GameRoomData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameRoomDao {

    public Long save(GameRoomData data, Connection connection) {
        String sql = "INSERT INTO game_room (current_turn, winner, cha_score, han_score) VALUES (?, ?, ?, ?)";
        try(PreparedStatement roomStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            roomStatement.setString(1, data.currentTurn());
            roomStatement.setString(2, data.winner());
            roomStatement.setDouble(3, data.choScore());
            roomStatement.setDouble(4, data.hanScore());
            roomStatement.executeUpdate();
            ResultSet resultSet = roomStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new SQLException("[ERROR] 게임 보드 생성 실패");
            }
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 방 데이터를 DB에 저장하는 중 오류가 발생했습니다.", e);
        }
    }

    public void update(Long roomId, GameRoomData data, Connection connection) {
        String sql = "UPDATE game_room SET current_turn = ?, winner = ?, cha_score = ?, han_score = ? WHERE id = ?";
        try (PreparedStatement roomStatement = connection.prepareStatement(sql)) {
            roomStatement.setString(1, data.currentTurn());
            roomStatement.setString(2, data.winner());
            roomStatement.setDouble(3, data.choScore());
            roomStatement.setDouble(4, data.hanScore());
            roomStatement.setLong(5, roomId);
            roomStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 이동에 따른 갱신을 DB에 저장하는 중 오류가 발생했습니다.", e);
        }
    }

    public Optional<GameRoomData> findRoomById(Long roomId, Connection connection) {
        String sql = "SELECT current_turn, winner, cha_score, han_score FROM game_room WHERE id=?";
        try (PreparedStatement roomStatement = connection.prepareStatement(sql)) {
            roomStatement.setLong(1, roomId);
            ResultSet resultSet = roomStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new GameRoomData(
                        resultSet.getString("current_turn"),
                        resultSet.getString("winner"),
                        resultSet.getDouble("cha_score"),
                        resultSet.getDouble("han_score")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 해당 방을 DB에서 조회하는 중 오류가 발생했습니다.", e);
        }
    }
}
