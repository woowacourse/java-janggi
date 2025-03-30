package domain.dao;


import domain.Country;
import domain.JanggiCoordinate;
import domain.dto.GameRoomDTO;
import domain.piece.Piece;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiGameDao {
    private final Connection connection;

    public JanggiGameDao(Connection connection) {
        this.connection = connection;
    }

    public void createGameTableIfNotExist() {
        String createGameTableSQL = """
                CREATE TABLE if not exists game(
                game_id INT AUTO_INCREMENT PRIMARY KEY,
                room_name VARCHAR(20) NOT NULL,
                curr_turn VARCHAR(20) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(createGameTableSQL);

        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] GAME TABLE 생성 실패");
        }
        throw new IllegalStateException("[ERROR] GAME TABLE 생성 실패");
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
        throw new IllegalStateException("[ERROR] GAME DELETE 실패");
    }

    public List<GameRoomDTO> findAllGames() {
        String findAllGamesSQL = "SELECT room_name, curr_turn, created_at from game;";

        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(findAllGamesSQL);
            List<GameRoomDTO> dtos = new ArrayList<>();
            while (resultSet.next()) {
                String roomName = resultSet.getString("room_name");
                String currTurn = resultSet.getString("curr_turn");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                dtos.add(new GameRoomDTO(roomName, currTurn, createdAt));
            }
            return dtos;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] GAME 조회 실패");
        }
    }

    public void saveGame(int gameId, Map<JanggiCoordinate, Piece> board) {
        JanggiCoordinateDao janggiCoordinateDao = new JanggiCoordinateDao(JanggiDao.getConnection());
        JanggiPieceDao janggiPieceDao = new JanggiPieceDao(JanggiDao.getConnection());

        for (Map.Entry<JanggiCoordinate, Piece> entry : board.entrySet()) {
            JanggiCoordinate coordinate = entry.getKey();
            Piece piece = entry.getValue();

            int pieceId = janggiPieceDao.addPiece(gameId, piece);
            janggiCoordinateDao.insertPieceToCoordinate(pieceId, coordinate, gameId);
        }
    }

    public int getGameIdByName(String gameName) {
        String findGameIdSQL = "SELECT game_id FROM game WHERE room_name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(findGameIdSQL)) {
            preparedStatement.setString(1, gameName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] GAME 조회 실패");
        }
        throw new IllegalStateException("[ERROR] GAME 조회 실패");
    }

    public String getCurrTurnById(int gameId) {
        String findCurrTurnSQL = "SELECT curr_turn FROM game WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(findCurrTurnSQL)) {
            preparedStatement.setInt(1, gameId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("[ERROR] CURRENT TURN 조회 실패!");
        }
        throw new IllegalStateException("[ERROR] CURRENT TURN 조회 실패!");
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
        throw new IllegalStateException("[ERROR] TURN 업데이트 실패");

    }
}
