package janggi.dao;

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
            return null;
        }
    }

    public void saveBoard(JanggiBoard board) {
        final String query = "INSERT INTO board (x, y, piece, side) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT(x, y) DO UPDATE SET piece = excluded.piece, side = excluded.side";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {
                preparedStatement.setInt(1, entry.getKey().getX());
                preparedStatement.setInt(2, entry.getKey().getY());
                preparedStatement.setString(3, entry.getValue().getSymbol().toString());
                preparedStatement.setString(4, entry.getValue().getSide().toString());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("장기판 저장 중 오류 발생", e);
        }
    }

    public void saveBoardStatus(JanggiBoard board) {
        final String query = "UPDATE board_status SET status = ?";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            String status = board.getStatus().toString();
            preparedStatement.setString(1, status);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("장기판 저장 중 오류 발생", e);
        }
    }

    public Map<Position, Piece> loadBoard() {
        final String query = "SELECT x, y, piece, side FROM board";
        Map<Position, Piece> boardMap = new HashMap<>();

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query);
             final ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                String pieceSymbol = resultSet.getString("piece");
                String side = resultSet.getString("side");

                Position position = new Position(x, y);
                Piece piece = PieceFactory.createPiece(pieceSymbol, side);
                boardMap.put(position, piece);
            }

        } catch (SQLException e) {
            throw new RuntimeException("장기판 불러오기 중 오류 발생", e);
        }

        return boardMap;
    }

    public BoardStatus loadBoardStatus() {
        final String query = "SELECT status FROM board_status";
        BoardStatus boardStatus = null;

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query);
             final ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String status = resultSet.getString("status");
                boardStatus = BoardStatus.valueOf(status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return boardStatus;
    }

}
