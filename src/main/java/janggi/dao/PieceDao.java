package janggi.dao;

import janggi.dao.dto.PieceFindDto;
import janggi.domain.Turn;
import janggi.domain.piece.PieceType;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static janggi.dao.DatabaseManager.executePreparedStatement;

public class PieceDao {

    public List<PieceFindDto> findAllPieces() {
        final String query = "SELECT * FROM Piece";
        return executePreparedStatement(query, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PieceFindDto> pieces = new ArrayList<>();
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                String side = resultSet.getString("side");
                pieces.add(new PieceFindDto(type, side));
            }
            return pieces;
        });
    }

    public void addPiece(PieceType pieceType, Turn turn) {
        final String query = "INSERT INTO Piece (type, side) VALUES (?, ?)";
        executePreparedStatement(query, preparedStatement -> {
            preparedStatement.setString(1, pieceType.getSymbol());
            preparedStatement.setString(2, turn.getName());
            return preparedStatement.executeUpdate();
        });
    }

    public int findPieceByTypeAndSide(final PieceType pieceType, final Turn turn) {
        final String selectPieceIdQuery = "SELECT piece_id FROM Piece WHERE type = ? AND side = ?";
        return executePreparedStatement(selectPieceIdQuery, preparedStatement -> {
            preparedStatement.setString(1, pieceType.getSymbol());
            preparedStatement.setString(2, turn.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new IllegalArgumentException("[ERROR] 기물 조회 중 오류가 발생했습니다.");
            }
            return resultSet.getInt("piece_id");
        });
    }
}
