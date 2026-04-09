package db.repository;

import db.converter.PieceConverter;
import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceRepository {

    public void save(
            Map<Intersection, Piece> pieces,
            int gameId,
            Connection connection
    ) throws SQLException {
        String save = "INSERT INTO piece (`row`, file, side, type, game_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(save)) {
            batchPieces(statement, pieces, gameId);

            statement.executeBatch();
        }
    }

    public Map<Intersection, Piece> findByGameId(
            int gameId,
            Connection connection
    ) throws SQLException {
        String findByGameId = "SELECT `row`, file, side, type FROM piece WHERE piece.game_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(findByGameId)) {
            statement.setInt(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return parsePieces(resultSet);
            }
        }
    }

    public void update(
            Map<Intersection, Piece> currentPieces,
            Map<Intersection, Piece> piecesBeforeLastMove,
            int gameId,
            Connection connection
    ) throws SQLException {
        List<Intersection> removedPieceIntersections = detectRemovedPieceIntersections(currentPieces, piecesBeforeLastMove);
        for (Intersection removedPieceIntersection : removedPieceIntersections) {
            deletePieceByIntersection(removedPieceIntersection, gameId, connection);
        }

        Map<Intersection, Piece> createdPieces = detectCreatedPieces(currentPieces, piecesBeforeLastMove);
        save(createdPieces, gameId, connection);
    }

    public void delete(
            int gameId,
            Connection connection
    ) throws SQLException {
        String delete = "DELETE FROM piece WHERE game_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
        }
    }

    private void deletePieceByIntersection(
            Intersection intersection,
            int gameId,
            Connection connection
    ) throws SQLException {
        String deleteByIntersection = "DELETE FROM piece WHERE game_id = ? AND `row` = ? AND file = ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteByIntersection)) {
            statement.setInt(1, gameId);
            statement.setInt(2, intersection.getRow());
            statement.setInt(3, intersection.getFile());

            statement.executeUpdate();
        }
    }

    private void batchPieces(
            PreparedStatement statement,
            Map<Intersection, Piece> pieces,
            int gameId
    ) throws SQLException {
        for (Map.Entry<Intersection, Piece> placedPiece : pieces.entrySet()) {
            setPieceToStatement(statement, placedPiece, gameId);
            statement.addBatch();
        }
    }

    private void setPieceToStatement(
            PreparedStatement statement,
            Map.Entry<Intersection, Piece> placedPiece,
            int gameId
    ) throws SQLException {
        Intersection intersection = placedPiece.getKey();
        Piece piece = placedPiece.getValue();
        Side side = piece.getSide();

        statement.setInt(1, intersection.getRow());
        statement.setInt(2, intersection.getFile());
        statement.setString(3, side.toString());
        statement.setString(4, PieceConverter.toColumnValue(piece));
        statement.setInt(5, gameId);
    }

    private List<Intersection> detectRemovedPieceIntersections(
            Map<Intersection, Piece> currentPieces,
            Map<Intersection, Piece> piecesBeforeLastMove
    ) {
        List<Intersection> removedPieceIntersections = new ArrayList<>();

        for (Map.Entry<Intersection, Piece> piece : piecesBeforeLastMove.entrySet()) {
            Piece currentPiece = currentPieces.get(piece.getKey());
            if (!piece.getValue().equals(currentPiece)) {
                removedPieceIntersections.add(piece.getKey());
            }
        }

        return removedPieceIntersections;
    }

    private Map<Intersection, Piece> detectCreatedPieces(
            Map<Intersection, Piece> currentPieces,
            Map<Intersection, Piece> piecesBeforeLastMove
    ) {
        Map<Intersection, Piece> createdPieces = new HashMap<>();

        for (Map.Entry<Intersection, Piece> piece : currentPieces.entrySet()) {
            Piece pieceBeforeMove = piecesBeforeLastMove.get(piece.getKey());
            if (!piece.getValue().equals(pieceBeforeMove)) {
                createdPieces.put(piece.getKey(), piece.getValue());
            }
        }

        return createdPieces;
    }

    private Map<Intersection, Piece> parsePieces(ResultSet resultSet) throws SQLException {
        Map<Intersection, Piece> pieces = new HashMap<>();

        while (resultSet.next()) {
            Intersection intersection = parseIntersection(resultSet);
            Piece piece = parsePiece(resultSet);

            pieces.put(intersection, piece);
        }

        return pieces;
    }

    private Intersection parseIntersection(ResultSet resultSet) throws SQLException {
        int row = resultSet.getInt(1);
        int file = resultSet.getInt(2);

        return new Intersection(row, file);
    }

    private Piece parsePiece(ResultSet resultSet) throws SQLException {
        Side side = Side.valueOf(resultSet.getString(3));
        String pieceName = resultSet.getString(4);

        return PieceConverter.toDomain(pieceName, side);
    }
}
