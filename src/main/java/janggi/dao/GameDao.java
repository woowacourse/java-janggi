package janggi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class GameDao {

    private final DataSource dataSource;

    public GameDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long save() {
        String sql = "INSERT INTO game VALUES ()";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new IllegalStateException("[ERROR] 게임 ID 생성에 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 저장 중 오류가 발생했습니다.", e);
        }
    }

    public boolean findById(long gameId) {
        String sql = "SELECT 1 FROM game WHERE id = ?";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setLong(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 조회 중 오류가 발생했습니다.", e);
        }
    }
}
