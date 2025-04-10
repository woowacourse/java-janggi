package janggi.dao;

import janggi.game.GameRoom;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.Team;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {

    private final DatabaseConnector connector;

    public BoardDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void saveAllBoardPiece(Map<Position,Piece> pieces, GameRoom gameRoom) {
        String query = "INSERT INTO board_piece (room_name, piece_type, team, column_position, row_position) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            for (Map.Entry<Position, Piece> pieceInfo : pieces.entrySet()) {
                Position position = pieceInfo.getKey();
                Piece piece = pieceInfo.getValue();
                preparedStatement.setString(1, gameRoom.getRoomName());
                preparedStatement.setString(2, piece.getPieceType().name());
                preparedStatement.setString(3, piece.getTeam().name());
                preparedStatement.setInt(4, position.column());
                preparedStatement.setInt(5, position.row());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBoardPiece(GameRoom gameRoom,Position startPosition, Position arrivedPosition) {
        String query = "UPDATE board_piece SET column_position = ?, row_position = ? WHERE column_position = ? AND row_position = ? AND room_name = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, arrivedPosition.column());
            preparedStatement.setInt(2, arrivedPosition.row());
            preparedStatement.setInt(3, startPosition.column());
            preparedStatement.setInt(4, startPosition.row());
            preparedStatement.setString(5, gameRoom.getRoomName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsBoardPieceByRoomName(String roomName) {
        String query = "SELECT EXISTS (SELECT * FROM board_piece WHERE room_name = ?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, roomName);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getBoolean(1);
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Piece> findAllBoardPieceByRoomName(String roomName) {
        String query = "SELECT * FROM board_piece WHERE room_name = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, roomName);
            ResultSet result = preparedStatement.executeQuery();
            Map<Position, Piece> locatedPieces = new HashMap<>();
            while (result.next()) {
                PieceType pieceType = PieceType.valueOf(result.getString("piece_type"));
                Team team = Team.valueOf(result.getString("team"));
                int columnPosition = result.getInt("column_position");
                int rowPosition = result.getInt("row_position");

                Position position = new Position(rowPosition, columnPosition);
                Piece piece = pieceType.createInstance(team);
                locatedPieces.put(position,piece);
            }
            return locatedPieces;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deletePieceByPositionAndRoomName(Position position, GameRoom gameRoom) {
        String query = "DELETE FROM board_piece WHERE column_position = ? AND row_position = ? AND room_name = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
             preparedStatement.setInt(1, position.column());
             preparedStatement.setInt(2, position.row());
             preparedStatement.setString(3, gameRoom.getRoomName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllByRoomName(GameRoom gameRoom) {
        String query = "DELETE FROM board_piece WHERE room_name = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, gameRoom.getRoomName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
