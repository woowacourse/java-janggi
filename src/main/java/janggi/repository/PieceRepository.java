package janggi.repository;

import javax.sql.DataSource;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.PlacedPiece;
import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PieceRepository {

    private final DataSource dataSource;

    public PieceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long save(PlacedPiece placedPiece) {
        String sql = "INSERT INTO pieces(game_room_id, camp, piece_type, row_position, col_position)" +
                " VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, placedPiece.getGameRoomId());
            preparedStatement.setString(2, placedPiece.getCampType().name());
            preparedStatement.setString(3, placedPiece.getPieceRule().name());
            preparedStatement.setInt(4, placedPiece.getRowPosition());
            preparedStatement.setInt(5, placedPiece.getColPosition());

            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
            return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessage.PIECE_SAVE_ERROR.getMessage(
                    placedPiece.getGameRoomId(), placedPiece.getRowPosition(), placedPiece.getColPosition()), e);
        }
    }

    public Map<Position, Piece> findByGameRoomId(long gameRoomId) {
        String sql = "SELECT * FROM pieces" +
                " WHERE game_room_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameRoomId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                Map<Position, Piece> pieces = new HashMap<>();
                while (rs.next()) {
                    Position position = new Position(rs.getInt("row_position"), rs.getInt("col_position"));
                    Piece piece = new Piece(PieceRule.valueOf(rs.getString("piece_type")), CampType.valueOf(rs.getString("camp")));
                    pieces.put(position, piece);
                }
                return pieces;
            }
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessage.PIECE_FIND_ERROR.getMessage(gameRoomId), e);
        }
    }

    public PlacedPiece findByGameIdAndPosition(long gameRoomId, int row, int column) {
        String sql = "SELECT * FROM pieces " +
                "WHERE game_room_id = ? AND row_position = ? AND col_position = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameRoomId);
            preparedStatement.setInt(2, row);
            preparedStatement.setInt(3, column);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new PlacedPiece(
                            rs.getLong("piece_id"),
                            rs.getLong("game_room_id"),
                            CampType.valueOf(rs.getString("camp")),
                            PieceRule.valueOf(rs.getString("piece_type")),
                            rs.getInt("row_position"),
                            rs.getInt("col_position")
                    );
                }
                throw new IllegalArgumentException(ExceptionMessage.PIECE_NOT_FOUND.getMessage(gameRoomId, row, column));
            }
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessage.PIECE_FIND_ERROR.getMessage(gameRoomId), e);
        }
    }

    public void update(PlacedPiece placedPiece) {
        String sql = "UPDATE pieces" +
                " SET game_room_id = ?, camp = ?, piece_type = ?, row_position = ?, col_position = ? " +
                "WHERE piece_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, placedPiece.getGameRoomId());
            preparedStatement.setString(2, placedPiece.getCampType().name());
            preparedStatement.setString(3, placedPiece.getPieceRule().name());
            preparedStatement.setInt(4, placedPiece.getRowPosition());
            preparedStatement.setInt(5, placedPiece.getColPosition());
            preparedStatement.setLong(6, placedPiece.getPlacedPieceId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessage.PIECE_UPDATE_ERROR.getMessage(
                    placedPiece.getGameRoomId(), placedPiece.getRowPosition(), placedPiece.getColPosition()), e);
        }
    }

    public void delete(PlacedPiece placedPiece) {
        String sql = "DELETE FROM pieces " +
                "WHERE piece_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, placedPiece.getPlacedPieceId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessage.PIECE_DELETE_ERROR.getMessage(placedPiece.getPlacedPieceId()), e);
        }
    }
}
