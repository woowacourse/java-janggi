package dao;

import domain.board.Point;
import domain.pieces.PieceDefinition;
import dto.BoardLocation;
import dto.BoardLocations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BoardLocationDAO {
    private final Connector connector;

    public BoardLocationDAO(final Connector connector) {
        this.connector = Objects.requireNonNull(connector, "connecter가 null일 수 없습니다.");
    }

    public void createBatch(final BoardLocations boardLocations) {
        final String query = "INSERT INTO board_location (location_piece, location_row, location_column, player_id) "
                + "VALUES (?,?,?,?)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (final BoardLocation boardLocation : boardLocations) {
                preparedStatement.setString(1, boardLocation.getPiece());
                preparedStatement.setInt(2, boardLocation.getRow());
                preparedStatement.setInt(3, boardLocation.getColumn());
                preparedStatement.setInt(4, boardLocation.getPlayerId());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 위치 정보를 생성하는 데 실패했습니다: " + e);
        }
    }

    public void deleteLocationAt(final Point point, final int gameId) {
        final String deleteQuery = "DELETE l FROM board_location l JOIN player pl ON l.player_id = pl.id "
                + "WHERE l.location_row = ? AND l.location_column = ? AND pl.game_id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, point.row());
            deleteStatement.setInt(2, point.column());
            deleteStatement.setInt(3, gameId);
            deleteStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 위치 정보를 삭제하는 데 실패했습니다: " + e);
        }
    }

    public void updateLocation(final Point start, final Point arrival, final int gameId) {
        final String updateQuery = "UPDATE board_location l JOIN player pl ON l.player_id = pl.id "
                + "SET l.location_row = ?, l.location_column = ? "
                + "WHERE l.location_row = ? AND l.location_column = ? AND pl.game_id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, arrival.row());
            updateStatement.setInt(2, arrival.column());
            updateStatement.setInt(3, start.row());
            updateStatement.setInt(4, start.column());
            updateStatement.setInt(5, gameId);
            updateStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 위치 정보를 수정하는 데 실패했습니다: " + e);
        }
    }

    public BoardLocations findAllByGameId(final int gameId) {
        final String query = "SELECT l.*, p.*, g.id FROM board_location l "
                + "JOIN player p ON l.player_id = p.id "
                + "JOIN game g ON p.game_id = g.id "
                + "WHERE g.id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            return convertResultSetToLocations(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 위치 정보를 조회하는 데 실패했습니다: " + e);
        }
    }

    private BoardLocations convertResultSetToLocations(final ResultSet resultSet) throws SQLException {
        final List<BoardLocation> locations = new ArrayList<>();
        while (resultSet.next()) {
            final BoardLocation location = convertResultSetToLocation(resultSet);
            locations.add(location);
        }
        return new BoardLocations(locations);
    }

    private BoardLocation convertResultSetToLocation(final ResultSet resultSet) throws SQLException {
        final Point point = new Point(resultSet.getInt("location_row"),
                resultSet.getInt("location_column"));
        final PieceDefinition pieceDefinition = PieceDefinition.valueOf(resultSet.getString("location_piece"));
        return new BoardLocation(
                point,
                pieceDefinition,
                resultSet.getInt("player_id")
        );
    }
}
