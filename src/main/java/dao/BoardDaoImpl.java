package dao;

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

    private final Connection connection;

    public BoardDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean hasRecords() {
        final String query = "SELECT COUNT(*) FROM board";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Map<Point, Piece> load() {
        Map<Point, Piece> board = new HashMap<>();
        final String query = "SELECT * FROM board";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int row = resultSet.getInt("point_row");
                int column = resultSet.getInt("point_column");
                String team = resultSet.getString("team");
                String pieceType = resultSet.getString("piece_type");
                board.put(Point.of(row, column), PieceType.createPiece(team, pieceType));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return board;
    }

    @Override
    public void save(final Point point, final Piece piece) {
        final String query = "INSERT INTO board (point_row, point_column, team, piece_type) VALUES(?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, point.row());
            preparedStatement.setInt(2, point.column());
            preparedStatement.setString(3, piece.team().name());
            preparedStatement.setString(4, piece.type().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAll() {
        final String query = "DELETE FROM board";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
