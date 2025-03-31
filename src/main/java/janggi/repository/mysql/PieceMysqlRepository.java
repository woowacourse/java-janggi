package janggi.repository.mysql;

import janggi.piece.Piece;
import janggi.repository.PieceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceMysqlRepository implements PieceRepository {

    private final Connection connection;

    public PieceMysqlRepository(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveAll(final long gameId, final List<Piece> pieces) {
        final String sql = """
                INSERT INTO piece (game_id, row_pos, column_pos, type, team)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (final Piece piece : pieces) {
                preparedStatement.setLong(1, gameId);
                preparedStatement.setInt(2, piece.getPosition().row().value());
                preparedStatement.setInt(3, piece.getPosition().column().value());
                preparedStatement.setString(4, piece.getType().name());
                preparedStatement.setString(5, piece.getTeam().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException("기물 저장 중 오류 발생", e);
        }
    }

    @Override
    public List<Piece> findAllByGameId(final long gameId) {
        final String sql = """
                SELECT row_pos, column_pos, type, team
                FROM piece
                WHERE game_id = ?
                """;

        final List<Piece> result = new ArrayList<>();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            try (final ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    final int row = rs.getInt("row_pos");
                    final int column = rs.getInt("column_pos");
                    final String type = rs.getString("type");
                    final String team = rs.getString("team");

                    result.add(Piece.of(row, column, type, team));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException("기물 조회 중 오류 발생", e);
        }

        return result;
    }
}
