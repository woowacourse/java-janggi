package repository;

import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import domain.state.JanggiGame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoardRepository {
    public void updatePosition(Connection conn, long gameId, Position from, Position to) {
        String sql = "UPDATE BOARD SET position_row = ?, position_column = ? WHERE game_room_id = ? and position_row = ? and position_column = ?";

        try (
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(1, to.getRow().getValue());
            psmt.setInt(2, to.getColumn().getValue());
            psmt.setLong(3, gameId);
            psmt.setInt(4, from.getRow().getValue());
            psmt.setInt(5, from.getColumn().getValue());

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Connection conn, long gameId, Position to) {
        String sql = "DELETE FROM BOARD WHERE game_room_id = ? and position_row = ? and position_column = ?";

        try (
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setLong(1, gameId);
            psmt.setInt(2, to.getRow().getValue());
            psmt.setInt(3, to.getColumn().getValue());

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(Connection conn, JanggiGame game, long gameId) {
        String sql = "INSERT INTO BOARD (team, piece_type, position_row, position_column, game_room_id) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement psmt = conn.prepareStatement(sql);
        ) {
            for (Entry<Position, Piece> entry : game.getBoard().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                psmt.setString(1, piece.getTeam().name());
                psmt.setString(2, piece.getPieceType().name());
                psmt.setInt(3, position.getRow().getValue());
                psmt.setInt(4, position.getColumn().getValue());
                psmt.setLong(5, gameId);

                psmt.addBatch();
                psmt.clearParameters();
            }
            psmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Map<Position, Piece> load(long gameId) {
        String sql = "SELECT team, piece_type, position_row, position_column FROM BOARD WHERE game_room_id = ?";

        Map<Position, Piece> pieces = new HashMap<>();
        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setLong(1, gameId);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    Team team = Team.valueOf(rs.getString(1));
                    PieceType pieceType = PieceType.valueOf(rs.getString(2));
                    Row row = new Row(rs.getInt(3));
                    Column column = new Column(rs.getInt(4));
                    Position position = Position.of(row, column);

                    Piece piece = PieceFactory.create(pieceType, team);
                    pieces.put(position, piece);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pieces;
    }
}
