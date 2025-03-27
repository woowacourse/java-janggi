package janggi.dao;

import janggi.domain.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameRoomDAO {

    private static final String FIND_QUERY = "SELECT NAME FROM game_room WHERE NAME = ?";
    private static final String INSERT_QUERY = "INSERT INTO game_room(name, turn) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE game_room SET TURN = ? WHERE NAME = ?";
    private static final String DELETE_QUERY = "DELETE FROM game_room WHERE  NAME = ?";

    private final Connection connection;

    public GameRoomDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean exist(String gameRoomName) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(FIND_QUERY)) {
            pstmt.setString(1, gameRoomName);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        }
    }

    public void create(String gameRoomName) throws SQLException {
        if (exist(gameRoomName)) {
            throw new IllegalArgumentException("이미 중복된 방 제목입니다.");
        }

        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY)) {
            pstmt.setString(1, gameRoomName);
            pstmt.setString(2, Team.CHO.toString());

            pstmt.executeUpdate();
        }
    }

    public void save(String gameRoomName, Team team) throws SQLException {
        if (!exist(gameRoomName)) {
            throw new IllegalArgumentException("해당 방이 존재하지 않습니다!");
        }

        try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY)) {
            pstmt.setString(1, team.toString());
            pstmt.setString(2, gameRoomName);

            pstmt.executeUpdate();
        }
    }

    public void delete(String gameRoomName) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY)) {
            pstmt.setString(1, gameRoomName);

            pstmt.executeUpdate();
        }
    }
}
