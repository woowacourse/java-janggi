package janggi.repository.mysql;

import janggi.GameId;
import janggi.piece.Piece;
import janggi.repository.PieceRepository;
import janggi.repository.util.ResultSetReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PieceMysqlRepository implements PieceRepository {

    @Override
    public void saveAll(final Connection connection,
                        final GameId gameId,
                        final List<Piece> pieces) {
        final String sql = """
                INSERT INTO piece (game_id, row_pos, column_pos, type, team)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (final Piece piece : pieces) {
                preparedStatement.setLong(1, gameId.getValue());
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
    public List<Piece> findAllByGameId(final Connection connection,
                                       final GameId gameId) {
        final String sql = """
                SELECT row_pos, column_pos, type, team
                FROM piece
                WHERE game_id = ?
                """;

        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId.getValue());

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                return ResultSetReader.toList(resultSet,
                        result -> Piece.of(
                                result.getInt("row_pos"),
                                result.getInt("column_pos"),
                                result.getString("type"),
                                result.getString("team")));
            }
        } catch (final SQLException e) {
            throw new RuntimeException("기물 조회 중 오류 발생", e);
        }
    }

    @Override
    public void deleteByGameId(final Connection connection, final GameId gameId) {
        final String sql = """
                DELETE FROM piece WHERE game_id = ?
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, gameId.getValue());
            ps.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("기물 삭제 중 오류 발생", e);
        }
    }
}
