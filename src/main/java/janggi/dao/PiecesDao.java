package janggi.dao;

import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PiecesDao {

    private final Connection connection;

    public PiecesDao(Connection connection) {
        this.connection = connection;
    }

    public void savePieces(Map<Position, Piece> pieces) throws SQLException {
        final var query = "INSERT INTO pieces(position_x, position_y, piece_type, team) VALUES(?, ?, ?, ?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            for (Piece piece : pieces.values()) {
                preparedStatement.setInt(1, piece.getPosition().x());
                preparedStatement.setInt(2, piece.getPosition().y());
                preparedStatement.setString(3, piece.getPieceType().name());
                preparedStatement.setString(4, piece.getTeam().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    public void deletePieces() throws SQLException {
        final var query = "DELETE FROM pieces";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }

    public Map<Position, Piece> loadPieces() throws SQLException {
        final var query = "SELECT position_x, position_y, piece_type, team FROM pieces";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var loadedPieces = preparedStatement.executeQuery(query);
            Map<Position, Piece> pieces = new HashMap<>();

            while (loadedPieces.next()) {
                int x = loadedPieces.getInt("position_x");
                int y = loadedPieces.getInt("position_y");
                String pieceType = loadedPieces.getString("piece_type");
                String team = loadedPieces.getString("team");

                Position position = new Position(x, y);
                Piece piece = Piece.createPiece(PieceType.valueOf(pieceType), position,
                    Team.valueOf(team));
                pieces.put(piece.getPosition(), piece);
            }
            return pieces;
        }
    }
}
