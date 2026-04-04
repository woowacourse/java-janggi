package janggi.repository.dao;

import janggi.repository.entity.GameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameEntityDao {

    private static final String GAME_ID = "game_id";
    private static final String CURRENT_TEAM = "current_turn";

    public Long save(
            Connection con,
            String currentTurn
    ) {
        String sql = """
                INSERT INTO game(current_turn)
                VALUES (?)
                """;

        try (PreparedStatement psmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, currentTurn);
            psmt.executeUpdate();
            return getGeneratedKey(psmt);
        } catch (SQLException e) {
            throw new IllegalStateException("game 데이터 삽입에 실패했습니다.");
        }
    }

    private long getGeneratedKey(PreparedStatement psmt) throws SQLException {
        try(ResultSet rs = psmt.getGeneratedKeys()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    public Optional<GameEntity> findLatestGame(Connection con) {
        String sql = """
                SELECT *
                FROM game
                ORDER BY game_id DESC
                LIMIT 1
                """;

        try (
                PreparedStatement psmt = con.prepareStatement(sql);
                ResultSet rs = psmt.executeQuery()
        ){
            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(new GameEntity(
                    rs.getLong(GAME_ID),
                    rs.getString(CURRENT_TEAM)
            ));

        } catch (SQLException e) {
            throw new IllegalStateException("게임 조회에 실패했습니다.", e);
        }
    }

    public void deleteByGameId(Connection con, Long gameId) {
        String sql = """
                DELETE FROM game
                WHERE game_id = (?)
                """;

        try (PreparedStatement psmt = con.prepareStatement(sql)){
            psmt.setLong(1, gameId);
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("게임 삭제에 실패했습니다.", e);
        }
    }
}
