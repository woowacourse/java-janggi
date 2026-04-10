package janggi.repository.util;

import janggi.repository.util.transaction.TransactionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcQueryExecutor {

    private final TransactionManager transactionManager;

    public JdbcQueryExecutor(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public int update(String sql, Object... args) {
        Connection conn = transactionManager.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setArguments(pstmt, args);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터베이스 작업 실패: " + sql, e);
        }
    }

    public Long insert(String sql, Object... args) {
        Connection conn = transactionManager.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setArguments(pstmt, args);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            throw new IllegalStateException("생성된 ID를 가져올 수 없습니다: " + sql);
        } catch (SQLException e) {
            throw new IllegalStateException("데이터베이스 삽입 실패: " + sql, e);
        }
    }

    public <T> Optional<T> queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
        Connection conn = transactionManager.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setArguments(pstmt, args);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(rowMapper.mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터베이스 조회 실패: " + sql, e);
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        Connection conn = transactionManager.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setArguments(pstmt, args);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(rowMapper.mapRow(rs));
                }
                return results;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터베이스 목록 조회 실패: " + sql, e);
        }
    }

    private void setArguments(PreparedStatement pstmt, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
    }
}
