package janggi.dao;

import janggi.dto.BoardPieceDto;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardDao {

    private final DatabaseConnector connector;

    public BoardDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void saveAllBoardPiece(Map<Position,Piece> pieces) {
        String query = "INSERT INTO board_piece (piece_type, live_status, team, column_position, row_position) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            for (Map.Entry<Position, Piece> pieceInfo : pieces.entrySet()) {
                Position position = pieceInfo.getKey();
                Piece piece = pieceInfo.getValue();
                preparedStatement.setString(1, piece.getPieceType().name());
                preparedStatement.setString(2, piece.getTeam().name());
                preparedStatement.setInt(3, position.column());
                preparedStatement.setInt(4, position.row());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBoardPiece(Position position, Piece updatePiece) {
        String query = "UPDATE board_piece SET piece_type = ?, team = ? WHERE column_position = ? AND row_position = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, updatePiece.getPieceType().name());
            preparedStatement.setString(2, updatePiece.getTeam().name());

            preparedStatement.setInt(3, position.column());
            preparedStatement.setInt(4, position.row());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsBoardPiece() {
        String query = "SELECT EXISTS (SELECT 1 FROM board_piece)";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getBoolean(1);
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

/*    public Map<Position, Piece> findAllBoardPiece() {
        String query = "SELECT * FROM board_piece";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            ResultSet result = preparedStatement.executeQuery();
            List<Piece> pieces = new ArrayList<>();
            while (result.next()) {
                PieceType pieceType = PieceType.valueOf(result.getString("piece_type"));
                Team team = Team.valueOf(result.getString("team"));
                int columnPosition = result.getInt("column_position");
                int rowPosition = result.getInt("row_position");
                boolean liveStatus = result.getBoolean("live_status");
                Piece piece = pieceType.createInstance(new BoardPieceDto(team, new Position(rowPosition, columnPosition), liveStatus));
                pieces.add(piece);
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    public void deleteAll() {
        String query = "DELETE FROM board_piece";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
