package dao;

import db.DatabaseConnector;
import domain.board.Point;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardDaoImpl implements BoardDao {

    private final DatabaseConnector databaseConnector;

    public BoardDaoImpl(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public boolean hasRecords() {
        final String query = "SELECT COUNT(*) FROM board";
        try (final Connection connection = databaseConnector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 보드 기록 조회 중 오류가 발생했습니다. " + e.getMessage(), e);
        }
    }

    @Override
    public Map<Point, Piece> load(int boardId) {
        Map<Point, Piece> board = new HashMap<>();
        final String query = "SELECT point_row, point_column, team, piece_type FROM board WHERE board_id = ?";
        try (final Connection connection = databaseConnector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int row = resultSet.getInt("point_row");
                int column = resultSet.getInt("point_column");
                String team = resultSet.getString("team");
                String pieceType = resultSet.getString("piece_type");
                board.put(Point.of(row, column), PieceType.createPiece(team, pieceType));
            }
            return board;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 보드 기록 조회 중 오류가 발생했습니다. " + e.getMessage(), e);
        }
    }

    @Override
    public void save(final Connection connection, final Point point, final Piece piece, final int boardId) {
        final String query = "INSERT INTO board (board_id, point_row, point_column, team, piece_type) VALUES(?, ?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardId);
            preparedStatement.setInt(2, point.row());
            preparedStatement.setInt(3, point.column());
            preparedStatement.setString(4, piece.team().name());
            preparedStatement.setString(5, piece.type().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 보드 기록 저장 중 오류가 발생했습니다. " + e.getMessage(), e);
        }
    }

    @Override
    public void remove(final Connection connection, final int boardId) {
        final String query = "DELETE FROM board WHERE board_id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 보드 기록 삭제 중 오류가 발생했습니다. " + e.getMessage(), e);
        }
    }

    @Override
    public void removeAll(final Connection connection) {
        final String query = "DELETE FROM board";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 보드 기록 삭제 중 오류가 발생했습니다. " + e.getMessage(), e);
        }
    }
}
