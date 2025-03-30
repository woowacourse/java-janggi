package janggi.dao;

import janggi.dto.BoardPieceDto;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    private final DatabaseConnector connector;

    public BoardDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void saveAllBoardPiece(List<Piece> pieces) {
        String query = "INSERT INTO board_piece (`piece_type`, `live_status`, `team`, `column_position`, `row_position`) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            for (Piece piece : pieces) {
                preparedStatement.setString(1, piece.getPieceType().name());
                preparedStatement.setBoolean(2, piece.isLive());
                preparedStatement.setString(3, piece.getTeam().name());
                preparedStatement.setInt(4, piece.getPosition().column());
                preparedStatement.setInt(5, piece.getPosition().row());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBoardPiece(Piece previousPiece, Piece updatePiece) {
        String query = "UPDATE board_piece SET column_position = ?, row_position = ?, live_status = ? WHERE column_position = ? AND row_position = ? AND team = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, updatePiece.getPosition().column());
            preparedStatement.setInt(2, updatePiece.getPosition().row());
            preparedStatement.setBoolean(3, updatePiece.isLive());
            preparedStatement.setInt(4, previousPiece.getPosition().column());
            preparedStatement.setInt(5, previousPiece.getPosition().row());
            preparedStatement.setString(6, previousPiece.getTeam().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsBoardPiece() {
        String query = "SELECT COUNT(*) FROM board_piece";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            ResultSet result = preparedStatement.executeQuery();
            int count = 0;
            if (result.next()) {
                count = result.getInt(1);
            }
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Piece> findAllBoardPiece() {
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
    }
}
