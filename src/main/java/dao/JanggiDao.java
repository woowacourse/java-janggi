package dao;

import static dao.ConnectionUtil.getConnection;

import domain.piece.PieceSymbol;
import domain.piece.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JanggiDao {

    public void addPiece(final List<JanggiPieceEntity> janggiEntities) {
        final var query = "INSERT INTO janggi (game_id, `rank`, `file`, piece_symbol, side) VALUES(?, ?, ?, ?, ?)";

        try (final var connection = getConnection();
             final var ps = connection.prepareStatement(query)) {

            for (JanggiPieceEntity janggiEntity : janggiEntities) {
                ps.setLong(1, janggiEntity.getGameId());
                ps.setInt(2, janggiEntity.getRank());
                ps.setInt(3, janggiEntity.getFile());
                ps.setString(4, janggiEntity.getPieceSymbol().getSymbol());
                ps.setString(5, janggiEntity.getSide().toString());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<GameEntity> findGameById(Long gameId) {
        final var query = "SELECT * FROM game WHERE id = ?";

        try (final var connection = getConnection();
             final var ps = connection.prepareStatement(query)) {

            ps.setLong(1, gameId);
            try (final var rs = ps.executeQuery()) {
                if (rs.next()) {
                    GameEntity gameEntity = new GameEntity(
                            rs.getLong("game_id")
                    );
                    return Optional.of(gameEntity);
                }
                return Optional.empty();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addGame(GameEntity gameEntity) {
        final var query = "INSERT INTO game (id) VALUES (?)";

        try (final var connection = getConnection();
             final var ps = connection.prepareStatement(query)) {

            ps.setLong(1, gameEntity.getGameId());

            ps.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<JanggiPieceEntity> findAllPiecesById(Long gameId) {
        final var query = "SELECT * FROM janggi WHERE game_id = ?";

        List<JanggiPieceEntity> janggiPieceEntities = new ArrayList<>();
        try (final var connection = getConnection();
             final var ps = connection.prepareStatement(query)) {

            ps.setLong(1, gameId);
            try (final var rs = ps.executeQuery()) {
                while (rs.next()) {
                    JanggiPieceEntity janggiPieceEntity = new JanggiPieceEntity(
                            rs.getLong("game_id"),
                            rs.getInt("rank"),
                            rs.getInt("file"),
                            PieceSymbol.fromSymbol(rs.getString("piece_symbol")),
                            Side.valueOf(rs.getString("side"))
                    );
                    janggiPieceEntities.add(janggiPieceEntity);
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return janggiPieceEntities;
    }

    public void deletePiecesByGameId(Long gameId) {
        String sql = "DELETE FROM janggi WHERE game_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, gameId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("장기 말 삭제 실패");
        }
    }
}
