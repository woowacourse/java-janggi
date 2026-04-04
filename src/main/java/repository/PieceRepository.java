package repository;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import util.PieceFactory;

public class PieceRepository {
    private final Connection connection;

    public PieceRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Long gameId, Board board) {
        String sql = "INSERT INTO piece (game_id, row_index, column_index, piece_type, team) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for(Map.Entry<Position, Piece> entry : board.getPieces().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                pstmt.setLong(1, gameId);
                pstmt.setInt(2, position.getRow());
                pstmt.setInt(3, position.getColumn());
                pstmt.setString(4, String.valueOf(piece.getType()));
                pstmt.setString(5, String.valueOf(piece.getTeam()));

                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePieces(Long gameId, Board board) {
        deletePieces(gameId);
        save(gameId, board);
    }

    public Map<Position, Piece> findByGameId(Long gameId) {
        String sql = "SELECT row_index, column_index, piece_type, team FROM piece WHERE game_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();

            Map<Position, Piece> pieces = new HashMap<>();

            while(rs.next()) {
                Position position = Position.from(rs.getInt("row_index"), rs.getInt("column_index"));

                PieceType pieceType = PieceType.valueOf(rs.getString("piece_type"));
                Team team = Team.valueOf(rs.getString("team"));
                Piece piece = PieceFactory.createPiece(pieceType, team);

                pieces.put(position, piece);
            }

            return pieces;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePieces(Long gameId) {
        String sql = "DELETE FROM piece WHERE game_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
