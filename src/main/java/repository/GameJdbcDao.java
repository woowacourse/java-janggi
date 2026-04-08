package repository;

import entity.GameEntity;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class GameJdbcDao implements GameDao {

    @Override
    public GameEntity save(GameEntity game) {
        String sql = "insert into games(current_turn, status, created_at, updated_at) values(?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            OffsetDateTime now = OffsetDateTime.now();

            pstmt.setString(1, game.getCurrentTurn());
            pstmt.setString(2, game.getStatus());
            pstmt.setObject(3, now);
            pstmt.setObject(4, now);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                Long generatedId = rs.getLong(1);
                return new GameEntity(generatedId, game.getCurrentTurn(), game.getStatus(), game.getUpdatedAt());
            }
            throw new RuntimeException("[ERROR] ID 생성 실패");
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public void update(Long gameId, String turnName, String status) {
        String sql = "update games set current_turn = ?, status = ?, updated_at = ? where id = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setString(1, turnName);
            pstmt.setString(2, status);
            pstmt.setObject(3, OffsetDateTime.now());
            pstmt.setLong(4, gameId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public List<GameEntity> findAll() {
        String sql = "select * from games";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            ResultSet rs = pstmt.executeQuery();

            List<GameEntity> gameEntities = new ArrayList<>();
            while (rs.next()) {
                GameEntity game = new GameEntity(
                        rs.getLong("id"),
                        rs.getString("current_turn"),
                        rs.getString("status"),
                        rs.getTimestamp("updated_at")
                                .toInstant()
                                .atZone(ZoneId.of("Asia/Seoul"))
                                .toOffsetDateTime()
                );

                gameEntities.add(game);
            }
            return gameEntities;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
