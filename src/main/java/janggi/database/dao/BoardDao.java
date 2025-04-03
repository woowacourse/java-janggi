package janggi.database.dao;

import janggi.board.BoardStatus;
import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.PieceFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {

    public Connection getConnection() {
        String url = "jdbc:sqlite:database.db";
        try {
            return DriverManager.getConnection(url);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("DB 연결 중 오류 발생", e);
        }
    }

    public void saveBoard(JanggiBoard board) {
        final String boardQuery = "INSERT INTO board (piece_id, x, y, piece, side) VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT(piece_id) DO UPDATE SET x = excluded.x, y = excluded.y, piece = excluded.piece, side = excluded.side";
        final String statusQuery = "UPDATE board_status SET status = ?";

        try (final Connection connection = getConnection();
             final PreparedStatement boardStatement = connection.prepareStatement(boardQuery);
             final PreparedStatement statusStatement = connection.prepareStatement(statusQuery)) {

            for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {
                boardStatement.setInt(1, entry.getKey().hashCode());
                boardStatement.setInt(2, entry.getKey().getX());
                boardStatement.setInt(3, entry.getKey().getY());
                boardStatement.setString(4, entry.getValue().getSymbol().toString());
                boardStatement.setString(5, entry.getValue().getSide().toString());
                boardStatement.executeUpdate();
            }

            statusStatement.setString(1, board.getStatus().toString());
            statusStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("장기판 저장 중 오류 발생", e);
        }
    }

    public JanggiBoard loadBoard() {
        final String boardQuery = "SELECT x, y, piece, side FROM board";
        final String statusQuery = "SELECT status FROM board_status";

        Map<Position, Piece> boardMap = new HashMap<>();
        BoardStatus boardStatus = null;

        try (final Connection connection = getConnection();
             final PreparedStatement boardStatement = connection.prepareStatement(boardQuery);
             final PreparedStatement statusStatement = connection.prepareStatement(statusQuery);
             final ResultSet boardResultSet = boardStatement.executeQuery();
             final ResultSet statusResultSet = statusStatement.executeQuery()) {

            while (boardResultSet.next()) {
                int x = boardResultSet.getInt("x");
                int y = boardResultSet.getInt("y");
                String pieceSymbol = boardResultSet.getString("piece");
                String side = boardResultSet.getString("side");

                Position position = new Position(x, y);
                Piece piece = PieceFactory.createPiece(pieceSymbol, side);
                boardMap.put(position, piece);
            }

            if (statusResultSet.next()) {
                boardStatus = BoardStatus.valueOf(statusResultSet.getString("status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("장기판 불러오기 중 오류 발생", e);
        }

        return JanggiBoard.loadBoard(boardMap, boardStatus);
    }

}
