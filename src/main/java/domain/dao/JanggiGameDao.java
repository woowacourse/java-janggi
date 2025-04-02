package domain.dao;


import domain.Country;
import domain.dto.GameFindDto;
import domain.dto.GameRoomDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JanggiGameDao {
    private final Connection connection;

    public JanggiGameDao(Connection connection) {
        this.connection = connection;
    }

    public int createGame(String roomName, Country country) {
        String createGameSQL = "INSERT INTO game(room_name,curr_turn) VALUES (?,?);";
        try (PreparedStatement statement = connection.prepareStatement(createGameSQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, roomName);
            statement.setString(2, country.getName());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] GAME CREATE 실패");
        }
        throw new IllegalStateException("[ERROR] GAME CREATE 실패");
    }

    public void deleteGameRoom(int gameId) {
        String deleteGameSQL = "DELETE FROM game WHERE game_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteGameSQL)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] GAME DELETE 실패");
        }
    }

    public List<GameRoomDto> findGames(int offset, int pageSize) {
        String findGamesSQL = "SELECT room_name, curr_turn, created_at " +
                "FROM game " +
                "ORDER BY created_at " +
                "LIMIT " + pageSize + " OFFSET " + offset;


        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(findGamesSQL);
            List<GameRoomDto> dtos = new ArrayList<>();
            while (resultSet.next()) {
                String roomName = resultSet.getString("room_name");
                String currTurn = resultSet.getString("curr_turn");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                dtos.add(new GameRoomDto(roomName, currTurn, createdAt));
            }
            return dtos;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] GAME 조회 실패");
        }
    }

    public GameFindDto getGameByName(String gameName) {
        String findGameIdSQL = "SELECT game_id, curr_turn FROM game WHERE room_name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(findGameIdSQL)) {
            preparedStatement.setString(1, gameName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int gameId = resultSet.getInt("game_id");
                String currTurn = resultSet.getString("curr_turn");
                return new GameFindDto(gameId, Country.fromName(currTurn));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] GAME 조회 실패");
        }
        throw new IllegalStateException("[ERROR] GAME 조회 실패");
    }

    public void updateTurn(int gameId, String newTurn) {
        String updateTurnSQL = "UPDATE game SET curr_turn = ? WHERE game_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateTurnSQL)) {
            preparedStatement.setString(1, newTurn);
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] TURN 업데이트 실패");
        }
    }

    public int countGameRooms() {
        String countGameSQL = "SELECT COUNT(*) FROM game;";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(countGameSQL)) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임방 개수 조회 실패");
        }

        throw new IllegalStateException("[ERROR] 게임방 개수 조회 실패");
    }
}
