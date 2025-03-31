package janggi.dao;

import janggi.dao.dto.BoardPieceFindDto;
import janggi.dao.dto.PieceFindDto;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardPieceDao {

    public List<BoardPieceFindDto> findAllPieces() {
        final String query = "SELECT B.x, B.y, P.type, P.side FROM BoardPiece B JOIN Piece P ON B.piece_id = P.piece_id";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 기물 조회가 성공적으로 진행되지 않았습니다.");
        }
    }

    public void addPositionPiece(final int gameId, final int x, final int y, final Piece piece, final Side side) {
        final String selectPieceIdQuery = "SELECT piece_id FROM Piece WHERE type = ? AND side = ?";
        final String insertPieceQuery = "INSERT INTO BoardPiece(x, y, piece_id, game_id) VALUES(?,?,?,?)";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement selectPieceStatement = connection.prepareStatement(selectPieceIdQuery);
             final PreparedStatement insertPieceStatement = connection.prepareStatement(insertPieceQuery)) {

            selectPieceStatement.setString(1, piece.getType().getSymbol());
            selectPieceStatement.setString(2, side.getName());
            ResultSet selectResultSet = selectPieceStatement.executeQuery();
            if (!selectResultSet.next()) {
                throw new IllegalStateException("[ERROR] 기물 조회 중 오류가 발생했습니다.");
            }
            int pieceId = selectResultSet.getInt("piece_id");

            insertPieceStatement.setInt(1, x);
            insertPieceStatement.setInt(2, y);
            insertPieceStatement.setInt(3, pieceId);
            insertPieceStatement.setInt(4, gameId);

            insertPieceStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 기물 조회가 성공적으로 진행되지 않았습니다.");
        }
    }

    public void updatePiecePosition(final int boardPieceId, final Position destination) {
        final String updateQuery = "UPDATE BoardPiece SET x = ?, y = ? WHERE board_piece_id = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, destination.getX());
            preparedStatement.setInt(2, destination.getY());
            preparedStatement.setInt(3, boardPieceId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 기물의 좌표 이동에 실패했습니다.");
        }
    }

    public int findBoardPieceIdByPosition(final Position position) {
        final String query = "SELECT board_piece_id FROM BoardPiece WHERE x = ? AND y = ?";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.getX());
            preparedStatement.setInt(2, position.getY());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new IllegalStateException("[ERROR] 기물 조회에 실패했습니다.");
            }

            return resultSet.getInt("board_piece_id");
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 기물 조회가 성공적으로 진행되지 않았습니다.", e);
        }
    }

    public void deletePositionIfExists(final Position destination) {
        final String deleteQuery = "DELETE FROM BoardPiece WHERE x = ? AND y = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {

            deleteStmt.setInt(1, destination.getX());
            deleteStmt.setInt(2, destination.getY());

            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 데이터베이스에 문제가 발생했습니다.");
        }
    }

}
