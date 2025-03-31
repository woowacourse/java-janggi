package janggi.dao;

import janggi.dao.dto.PieceFindDto;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Side;

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

    public void addPiece(PieceType pieceType, Side side) {
        final String query = "INSERT INTO Piece (type, side) VALUES (?, ?)";
        executePreparedStatement(query, preparedStatement -> {
            preparedStatement.setString(1, pieceType.getSymbol());
            preparedStatement.setString(2, side.getName());
            return preparedStatement.executeUpdate();
        });
    }
}
