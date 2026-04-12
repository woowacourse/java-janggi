package janggi.repository.dao;

import janggi.domain.board.Location;
import janggi.repository.entity.PieceEntity;
import janggi.repository.util.JdbcQueryExecutor;
import janggi.repository.util.transaction.TransactionManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PieceDao {

    private final JdbcQueryExecutor jdbcQueryExecutor;

    public PieceDao(TransactionManager transactionManager) {
        this.jdbcQueryExecutor = new JdbcQueryExecutor(transactionManager);
    }

    public Long insert(PieceEntity pieceEntity) {
        String sql = "INSERT INTO piece (game_id, type, side, row_idx, col_idx) VALUES (?, ?, ?, ?, ?)";
        return jdbcQueryExecutor.insert(sql, pieceEntity.getGameId(), pieceEntity.getType(), pieceEntity.getSide(),
                pieceEntity.getRowIdx(), pieceEntity.getColIdx());
    }

    public Optional<PieceEntity> findById(Long id) {
        String sql = "SELECT id, game_id, type, side, row_idx, col_idx FROM piece WHERE id = ?";
        return jdbcQueryExecutor.queryForObject(sql, this::matToPieceEntity, id);
    }

    public Optional<PieceEntity> findByGameIdAndLocation(Long gameId, Location location) {
        String sql = "SELECT id, game_id, type, side, row_idx, col_idx FROM piece WHERE game_id = ? AND row_idx = ? AND col_idx = ?";
        return jdbcQueryExecutor.queryForObject(sql, this::matToPieceEntity, gameId, location.row(), location.col());
    }

    public List<PieceEntity> findByGameId(Long gameId) {
        String sql = "SELECT id, game_id, type, side, row_idx, col_idx FROM piece WHERE game_id = ?";
        return jdbcQueryExecutor.query(sql, this::matToPieceEntity, gameId);
    }

    public void updatePosition(Long id, Location to) {
        String sql = "UPDATE piece SET row_idx = ?, col_idx = ? WHERE id = ?";
        jdbcQueryExecutor.update(sql, to.row(), to.col(), id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM piece WHERE id = ?";
        jdbcQueryExecutor.update(sql, id);
    }

    private PieceEntity matToPieceEntity(ResultSet rs) throws SQLException {
        return new PieceEntity(
                rs.getLong("id"),
                rs.getLong("game_id"),
                rs.getString("type"),
                rs.getString("side"),
                rs.getInt("row_idx"),
                rs.getInt("col_idx")
        );
    }
}
