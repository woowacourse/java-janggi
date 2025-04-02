package janggi.dao;

import janggi.dao.dto.BoardPieceFindDto;
import janggi.domain.board.Position;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static janggi.dao.DatabaseManager.executePreparedStatement;

public class BoardPieceDao {

    public List<BoardPieceFindDto> findAllPieces() {
        final String query = "SELECT B.x, B.y, P.type, P.side FROM BoardPiece B JOIN Piece P ON B.piece_id = P.piece_id";
        return executePreparedStatement(query, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BoardPieceFindDto> pieces = new ArrayList<>();
            while (resultSet.next()) {
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                String pieceType = resultSet.getString("type");
                String side = resultSet.getString("side");

                pieces.add(new BoardPieceFindDto(x, y, pieceType, side));
            }
            return pieces;
        });
    }

    public void addPositionPiece(final int gameId, final int x, final int y, final int pieceId) {
        final String insertPieceQuery = "INSERT INTO BoardPiece(x, y, piece_id, game_id) VALUES(?,?,?,?)";
        executePreparedStatement(insertPieceQuery, preparedStatement -> {
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.setInt(3, pieceId);
            preparedStatement.setInt(4, gameId);

            return preparedStatement.executeUpdate();
        });
    }

    public void updatePiecePosition(final int boardPieceId, final Position destination) {
        final String updateQuery = "UPDATE BoardPiece SET x = ?, y = ? WHERE board_piece_id = ?";
        executePreparedStatement(updateQuery, preparedStatement -> {
            preparedStatement.setInt(1, destination.getX());
            preparedStatement.setInt(2, destination.getY());
            preparedStatement.setInt(3, boardPieceId);

            return preparedStatement.executeUpdate();
        });
    }

    public int findBoardPieceIdByPosition(final Position position) {
        final String query = "SELECT board_piece_id FROM BoardPiece WHERE x = ? AND y = ?";
        return executePreparedStatement(query, preparedStatement -> {
            preparedStatement.setInt(1, position.getX());
            preparedStatement.setInt(2, position.getY());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new IllegalStateException("[ERROR] 기물 조회에 실패했습니다.");
            }
            return resultSet.getInt("board_piece_id");
        });
    }

    public void deletePositionIfExists(final Position destination) {
        final String deleteQuery = "DELETE FROM BoardPiece WHERE x = ? AND y = ?";
        executePreparedStatement(deleteQuery, preparedStatement -> {
            preparedStatement.setInt(1, destination.getX());
            preparedStatement.setInt(2, destination.getY());
            return preparedStatement.executeUpdate();
        });
    }

}
