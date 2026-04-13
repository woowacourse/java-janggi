package data;

import domain.place.piece.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class JdbcGameDao implements GameDao {

    @Override
    public GameEntity insert(Connection conn, GameEntity gameEntity) {
        String sql = """
                INSERT INTO game (player_cho, player_han, current_turn, status)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, gameEntity.playerCho());
            ps.setString(2, gameEntity.playerHan());
            ps.setString(3, gameEntity.currentTurn().name());
            ps.setBoolean(4, gameEntity.status());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return new GameEntity(
                            rs.getLong(1),
                            gameEntity.playerCho(),
                            gameEntity.playerHan(),
                            gameEntity.currentTurn(),
                            gameEntity.status()
                    );
                }
                throw new SQLException("[ERROR] 생성된 id를 가져오지 못했습니다.");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] game 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public GameEntity update(Connection conn, GameEntity gameEntity) {
        String sql = """
                UPDATE game
                SET player_cho = ?, player_han = ?, current_turn = ?, status = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gameEntity.playerCho());
            ps.setString(2, gameEntity.playerHan());
            ps.setString(3, gameEntity.currentTurn().name());
            ps.setBoolean(4, gameEntity.status());
            ps.setLong(5, gameEntity.id());

            ps.executeUpdate();
            return gameEntity;

        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] game 업데이트 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Optional<GameEntity> findById(Connection conn, Long id) {
        String sql = """
                SELECT id, player_cho, player_han, current_turn, status
                FROM game
                WHERE id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                return Optional.of(toGameDto(rs));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] 게임 검색 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void deleteById(Connection conn, Long id) {
        String sql = "DELETE FROM game WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("game 삭제 중 오류가 발생했습니다. id=" + id, e);
        }
    }

    private GameEntity toGameDto(ResultSet rs) throws SQLException {
        return new GameEntity(
                rs.getLong("id"),
                rs.getString("player_cho"),
                rs.getString("player_han"),
                Side.valueOf(rs.getString("current_turn")),
                rs.getBoolean("status")
        );
    }
}
