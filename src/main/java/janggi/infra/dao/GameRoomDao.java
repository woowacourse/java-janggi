package janggi.infra.dao;

import janggi.infra.dto.GameRoomData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameRoomDao {

    public Long save(GameRoomData data, Connection connection) throws SQLException {
        String sql = "INSERT INTO game_room (current_turn, winner, cha_score, han_score) VALUES (?, ?, ?, ?)";
        PreparedStatement roomStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
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
    }

    public void update(Long roomId, GameRoomData data, Connection connection) throws SQLException {
        String sql = "UPDATE game_room SET current_turn = ?, winner = ?, cha_score = ?, han_score = ? WHERE id = ?";
        PreparedStatement roomStatement = connection.prepareStatement(sql);
        roomStatement.setString(1, data.currentTurn());
        roomStatement.setString(2, data.winner());
        roomStatement.setDouble(3, data.choScore());
        roomStatement.setDouble(4, data.hanScore());
        roomStatement.setLong(5, roomId);
        roomStatement.executeUpdate();
    }

    public GameRoomData findRoomById(Long roomId, Connection connection) throws SQLException {
        String sql = "SELECT current_turn, winner, cha_score, han_score FROM game_room WHERE id=?";
        PreparedStatement roomStatement = connection.prepareStatement(sql);
        roomStatement.setLong(1, roomId);
        ResultSet resultSet = roomStatement.executeQuery();
        if (resultSet.next()) {
            return new GameRoomData(
                    resultSet.getString("current_turn"),
                    resultSet.getString("winner"),
                    resultSet.getDouble("cha_score"),
                    resultSet.getDouble("han_score")
            );
        }
        throw new IllegalArgumentException("[ERROR] 해당 방이 존재하지 않습니다.");
    }
}
