package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MoveLogDao {

    public void insert(Connection connection, long gameRoomId, MoveLogRawData log) {
        String sql = "INSERT INTO move_log "
                + "(game_room_id, seq, type, turn, from_row, from_col, to_row, to_col, piece_type) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameRoomId);
            statement.setInt(2, log.seq());
            statement.setString(3, log.type());
            statement.setString(4, log.turn());
            setNullableInt(statement, 5, log.fromRow());
            setNullableInt(statement, 6, log.fromCol());
            setNullableInt(statement, 7, log.toRow());
            setNullableInt(statement, 8, log.toCol());
            setNullableString(statement, 9, log.pieceType());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("이동 로그 저장 실패", e);
        }
    }

    public List<MoveLogRawData> findByGameRoomId(Connection connection, long gameRoomId) {
        String sql = "SELECT seq, type, turn, from_row, from_col, to_row, to_col, piece_type "
                + "FROM move_log WHERE game_room_id = ? ORDER BY seq";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameRoomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<MoveLogRawData> logs = new ArrayList<>();
                while (resultSet.next()) {
                    logs.add(mapRow(resultSet));
                }
                return logs;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("이동 로그 조회 실패", e);
        }
    }

    public void deleteByGameRoomId(Connection connection, long gameRoomId) {
        String sql = "DELETE FROM move_log WHERE game_room_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameRoomId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("이동 로그 삭제 실패", e);
        }
    }

    private MoveLogRawData mapRow(ResultSet resultSet) throws SQLException {
        return new MoveLogRawData(
                resultSet.getInt("seq"),
                resultSet.getString("type"),
                resultSet.getString("turn"),
                getNullableInt(resultSet, "from_row"),
                getNullableInt(resultSet, "from_col"),
                getNullableInt(resultSet, "to_row"),
                getNullableInt(resultSet, "to_col"),
                resultSet.getString("piece_type")
        );
    }

    private void setNullableInt(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.INTEGER);
            return;
        }
        statement.setInt(index, value);
    }

    private void setNullableString(PreparedStatement statement, int index, String value) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.VARCHAR);
            return;
        }
        statement.setString(index, value);
    }

    private Integer getNullableInt(ResultSet resultSet, String column) throws SQLException {
        int value = resultSet.getInt(column);
        if (resultSet.wasNull()) {
            return null;
        }
        return value;
    }
}
