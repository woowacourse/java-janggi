package janggi.dao;

import janggi.domain.Team;
import janggi.manager.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameRoomDAO {

    private static final String FIND_ALL_GAME_ROOM_QUERY = "SELECT NAME FROM game_room";
    private static final String EXIST_QUERY = "SELECT NAME FROM game_room WHERE NAME = ?";
    private static final String INSERT_QUERY = "INSERT INTO game_room(name, turn) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE game_room SET TURN = ? WHERE NAME = ?";
    private static final String DELETE_QUERY = "DELETE FROM game_room WHERE  NAME = ?";
    private static final String FIND_TURN_QUERY = "SELECT TURN FROM game_room WHERE NAME = ?";

    private final DatabaseManager databaseManager;

    public GameRoomDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public List<String> findAll() {
        List<String> names = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_ALL_GAME_ROOM_QUERY)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                names.add(name);
            }

            return names;
        } catch (SQLException e) {
            throw new RuntimeException("findAll 중 에러 발생", e);
        }
    }

    public Team findTurn(String gameRoomName) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_TURN_QUERY)) {

            pstmt.setString(1, gameRoomName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Team.valueOf(rs.getString("turn"));
            }
            throw new IllegalArgumentException("해당 방 이름이 존재하지 않습니다");
        } catch (SQLException e) {
            throw new RuntimeException("findTurn 중 에러 발생", e);
        }
    }

    public boolean exist(String gameRoomName) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(EXIST_QUERY)) {
            pstmt.setString(1, gameRoomName);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("exist 중 에러 발생", e);
        }
    }

    public void create(String gameRoomName) {
        if (exist(gameRoomName)) {
            throw new IllegalArgumentException("이미 중복된 방 제목입니다.");
        }

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY)) {

            pstmt.setString(1, gameRoomName);
            pstmt.setString(2, Team.CHO.toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("create 중 에러 발생", e);
        }
    }

    public void save(String gameRoomName, Team team) {
        if (!exist(gameRoomName)) {
            throw new IllegalArgumentException("해당 방이 존재하지 않습니다!");
        }

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY)) {
            pstmt.setString(1, team.toString());
            pstmt.setString(2, gameRoomName);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("save 중 에러 발생", e);
        }
    }

    public void delete(String gameRoomName) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY)) {
            pstmt.setString(1, gameRoomName);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("delete 중 에러 발생");
        }
    }
}
