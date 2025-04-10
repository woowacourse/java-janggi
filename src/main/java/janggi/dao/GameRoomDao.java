package janggi.dao;

import janggi.board.Turn;
import janggi.game.GameRoom;
import janggi.team.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameRoomDao {

    private final DatabaseConnector connector;

    public GameRoomDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void saveGameRoom(GameRoom gameRoom) {
        String query = "INSERT INTO game_room (room_name, turn) VALUES(?, ?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, gameRoom.getRoomName());
            preparedStatement.setString(2, gameRoom.getTurnTeam().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> findAllGameRoom() {
        String query = "SELECT room_name FROM game_room";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            List<String> roomNames = new ArrayList<>();
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String roomName = result.getString("room_name");
                roomNames.add(roomName);
            }
            return roomNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsGameRoom(String roomName) {
        String query = "SELECT EXISTS(SELECT * FROM game_room WHERE room_name = ?)";
        try(Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1,roomName);
            ResultSet result = preparedStatement.executeQuery();

            if(result.next()) {
                return result.getBoolean(1);
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GameRoom findByRoomName(String roomName) {
        String query = "SELECT * FROM game_room WHERE room_name = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, roomName);
            ResultSet result = preparedStatement.executeQuery();

            if(result.next()) {
                String findRoomName = result.getString("room_name");
                Team findTurn = Team.valueOf(result.getString("turn"));
                return new GameRoom(findRoomName, new Turn(findTurn));
            }
            throw new IllegalArgumentException("게임 방을 찾을 수 없습니다");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(String roomName, Turn turn) {
        String query = "UPDATE game_room SET turn = ? WHERE room_name = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
          preparedStatement.setString(1, turn.getTurn().name());
          preparedStatement.setString(2, roomName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
