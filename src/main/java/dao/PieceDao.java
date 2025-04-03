package dao;

import domain.position.Position;
import domain.unit.Team;
import domain.unit.UnitType;
import entity.Piece;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao extends DefaultDao implements EntityMapper<Piece> {

    public void save(final Piece piece, final String roomId) {
        final var query = """
                INSERT INTO piece(position_x, position_y, piece_type, team, room_id)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            ppst.setInt(1, piece.positionX());
            ppst.setInt(2, piece.positionY());
            ppst.setString(3, piece.unitType().toString());
            ppst.setString(4, piece.team().toString());
            ppst.setString(5, roomId);
            ppst.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Piece> findBoardsByRoomId(String roomId) {
        final var query = """
                SELECT * FROM piece
                WHERE room_id = ?
                """;
        final List<Piece> pieces = new ArrayList<>();
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            ppst.setString(1, roomId);
            final var resultSet = ppst.executeQuery();
            while (resultSet.next()) {
                pieces.add(mapFromResultSet(resultSet));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return pieces;
    }

    public Piece findBoardByPosition(String roomId, Position position) {
        final var query = """
                SELECT * FROM piece
                WHERE room_id = ? AND position_x = ? AND position_y = ?
                """;
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            ppst.setString(1, roomId);
            ppst.setInt(2, position.getX());
            ppst.setInt(3, position.getY());
            final var resultSet = ppst.executeQuery();
            if (resultSet.next()) {
                return mapFromResultSet(resultSet);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updatePosition(Long boardId, Position position) {
        final var query = """
                UPDATE piece
                SET position_x = ?, position_y = ?
                WHERE piece_id = ?
                """;
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            ppst.setInt(1, position.getX());
            ppst.setInt(2, position.getY());
            ppst.setLong(3, boardId);
            ppst.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long boardId) {
        final var query = """
                DELETE FROM piece
                WHERE piece_id = ?
                """;
        try (final var connection = getConnection();
             final var ppst = connection.prepareStatement(query)) {
            ppst.setLong(1, boardId);
            ppst.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Piece mapFromResultSet(ResultSet resultSet) throws SQLException {
        return new Piece(
                resultSet.getLong("piece_id"),
                resultSet.getInt("position_x"),
                resultSet.getInt("position_y"),
                UnitType.valueOf(resultSet.getString("piece_type")),
                Team.valueOf(resultSet.getString("team"))
        );
    }
}
