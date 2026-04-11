package data;

import java.sql.Connection;
import java.util.Optional;
import domain.place.piece.Side;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcGameDao implements GameDao {

    @Override
    public Long insert(Connection conn, GameDto gameDto) {
        String sql = """
                INSERT INTO game (player_cho, player_han, current_turn, status)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, gameDto.playerCho());
            ps.setString(2, gameDto.playerHan());
            ps.setString(3, gameDto.currentTurn().name());
            ps.setBoolean(4, gameDto.status());

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new SQLException("[ERROR] 생성된 id를 가져오지 못했습니다.");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] game 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void update(Connection conn, GameDto gameDto) {
        String sql = """
                UPDATE game
                SET player_cho = ?, player_han = ?, current_turn = ?, status = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gameDto.playerCho());
            ps.setString(2, gameDto.playerHan());
            ps.setString(3, gameDto.currentTurn().name());
            ps.setBoolean(4, gameDto.status());
            ps.setLong(5, gameDto.id());

        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] game 업데이트 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Optional<GameDto> findById(Connection conn, Long id) {
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
            throw new RuntimeException("game 삭제 중 오류가 발생했습니다. id=" + id, e);
        }
    }

    private GameDto toGameDto(ResultSet rs) throws SQLException {
        return new GameDto(
                rs.getLong("id"),
                rs.getString("player_cho"),
                rs.getString("player_han"),
                Side.valueOf(rs.getString("current_turn")),
                rs.getBoolean("status")
        );
    }
}
