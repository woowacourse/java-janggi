package repository;

import entity.GameEntity;

import java.sql.*;
import java.time.OffsetDateTime;

public class GameJdbcDao implements GameDao {

    @Override
    public GameEntity save(GameEntity game) {
        String sql = "insert into games(current_turn, status, created_at, updated_at) values(?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            OffsetDateTime now = OffsetDateTime.now();

            pstmt.setString(1, game.getCurrentTurn());
            pstmt.setString(2, game.getStatus());
            pstmt.setObject(3, now);
            pstmt.setObject(4, now);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                Long generatedId = rs.getLong(1);
                return new GameEntity(generatedId, game.getCurrentTurn(), game.getStatus());
            }
            throw new RuntimeException("[ERROR] ID 생성 실패");
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public void update(Long gameId, String turnName, String status) {
        String sql = "update games set current_turn = ?, status = ?, updated_at = ? where id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, turnName);
            pstmt.setString(2, status);
            pstmt.setObject(3, OffsetDateTime.now());
            pstmt.setLong(4, gameId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        } finally {
            close(con, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

    private void close(Connection con, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] " + e.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] " + e.getMessage());
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] " + e.getMessage());
            }
        }
    }
}
