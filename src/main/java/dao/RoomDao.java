package dao;

import domain.GameState;
import domain.unit.Team;
import entity.Room;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void save(Room room) {
        final var query = """
                INSERT INTO room(room_id, status, turn)
                VALUES (?, ?, ?)
                """;
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            ppst.setString(1, room.roomId());
            ppst.setString(2, room.status().toString());
            ppst.setString(3, room.turn().toString());
            ppst.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Room> findAllPlayingRoom() {
        final var query = """
                SELECT * FROM room
                WHERE status = 'PLAYING'
                ORDER BY room_id
                """;
        final List<Room> rooms = new ArrayList<>();
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            final var resultSet = ppst.executeQuery();
            while (resultSet.next()) {
                rooms.add(new Room(
                        resultSet.getString("room_id"),
                        GameState.valueOf(resultSet.getString("status")),
                        Team.valueOf(resultSet.getString("turn"))
                ));
            }
            return rooms;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Room findRoomById(String roomId) {
        final var query = """
                SELECT * FROM room
                WHERE room_id = ?
                """;
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            ppst.setString(1, roomId);
            final var resultSet = ppst.executeQuery();
            if (resultSet.next()) {
                return new Room(
                        resultSet.getString("room_id"),
                        GameState.valueOf(resultSet.getString("status")),
                        Team.valueOf(resultSet.getString("turn"))
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateStatus(String roomId, GameState status) {
        final var query = """
                UPDATE room
                SET status = ?
                WHERE room_id = ?
                """;
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            ppst.setString(1, status.toString());
            ppst.setString(2, roomId);
            ppst.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(String roomId, Team turn) {
        final var query = """
                UPDATE room
                SET turn = ?
                WHERE room_id = ?
                """;
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            ppst.setString(1, turn.toString());
            ppst.setString(2, roomId);
            ppst.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
