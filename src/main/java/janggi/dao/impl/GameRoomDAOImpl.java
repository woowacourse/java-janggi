package janggi.dao.impl;

import janggi.dao.GameRoomDAO;
import janggi.domain.Team;
import janggi.manager.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameRoomDAOImpl implements GameRoomDAO {

    private static final String FIND_ALL_GAME_ROOM_QUERY = "SELECT NAME FROM game_room";
    private static final String EXIST_QUERY = "SELECT NAME FROM game_room WHERE NAME = ?";
    private static final String INSERT_QUERY = "INSERT INTO game_room(name, turn) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE game_room SET TURN = ? WHERE NAME = ?";
    private static final String DELETE_QUERY = "DELETE FROM game_room WHERE NAME = ?";
    private static final String FIND_TURN_QUERY = "SELECT TURN FROM game_room WHERE NAME = ?";

    private final DatabaseManager databaseManager;

    public GameRoomDAOImpl(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public List<String> findAllNames() {
        List<String> names = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_ALL_GAME_ROOM_QUERY)) {

            ResultSet resultSet = pstmt.executeQuery();

            addName(resultSet, names);

            return names;
        } catch (final SQLException e) {
            throw new IllegalArgumentException("findAll 중 에러 발생", e);
        }
    }

    @Override
    public Team findTurn(String gameRoomName) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_TURN_QUERY)) {

            pstmt.setString(1, gameRoomName);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("turn"));
            }
            throw new IllegalArgumentException("해당 방 이름이 존재하지 않습니다");
        } catch (final SQLException e) {
            throw new IllegalArgumentException("findTurn 중 에러 발생", e);
        }
    }

    @Override
    public boolean exist(String gameRoomName) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(EXIST_QUERY)) {

            pstmt.setString(1, gameRoomName);
            ResultSet resultSet = pstmt.executeQuery();

            return resultSet.next();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("exist 중 에러 발생", e);
        }
    }

    @Override
    public void create(String gameRoomName) {
        if (exist(gameRoomName)) {
            throw new IllegalArgumentException("이미 중복된 방 제목입니다.");
        }

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY)) {

            pstmt.setString(1, gameRoomName);
            pstmt.setString(2, Team.CHO.toString());

            pstmt.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("create 중 에러 발생", e);
        }
    }

    @Override
    public void update(String gameRoomName, Team team) {
        if (!exist(gameRoomName)) {
            throw new IllegalArgumentException("해당 방이 존재하지 않습니다!");
        }

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, team.toString());
            pstmt.setString(2, gameRoomName);

            pstmt.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("save 중 에러 발생", e);
        }
    }

    @Override
    public void delete(String gameRoomName) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY)) {

            pstmt.setString(1, gameRoomName);

            pstmt.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("delete 중 에러 발생");
        }
    }

    private void addName(ResultSet resultSet, List<String> names) throws SQLException {
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            names.add(name);
        }
    }
}
