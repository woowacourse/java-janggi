package janggi.repository;

import javax.sql.DataSource;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PieceRepository {

    private final DataSource dataSource;

    public PieceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long save(long gameId, CampType campType, PieceRule pieceRule, int row, int column) {
        return insert("INSERT INTO pieces(game_id, camp, piece_type, row_position, col_position) VALUES(?, ?, ?, ?, ?)",
                gameId, campType.name(), pieceRule.name(), row, column);
    }

    private long insert(String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Piece> findByGameId(long gameId) {
        return executeQuery("SELECT * FROM pieces WHERE game_id = ?",
                rs -> {
                    Map<Position, Piece> pieces = new HashMap<>();
                    while (rs.next()) {
                        Position position = new Position(rs.getInt("row_position"), rs.getInt("col_position"));
                        Piece piece = new Piece(PieceRule.valueOf(rs.getString("piece_type")), CampType.valueOf(rs.getString("camp")));
                        pieces.put(position, piece);
                    }
                    return pieces;
                },
                gameId
        );
    }

    public void update(long gameId, Position source, Position destination) {
        update("UPDATE pieces SET row_position = ?, col_position = ? WHERE game_id = ? AND row_position = ? AND col_position = ?",
                destination.row(), destination.column(), gameId, source.row(), source.column());
    }

    public void delete(long gameId, Position destination) {
        update("DELETE FROM pieces WHERE game_id = ? AND row_position = ? AND col_position = ?",
                gameId, destination.row(), destination.column());
    }

    private void update(String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T executeQuery(String sql, ResultSetMapper<T> mapper, Object... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            ResultSet rs = preparedStatement.executeQuery();
            return mapper.map(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
