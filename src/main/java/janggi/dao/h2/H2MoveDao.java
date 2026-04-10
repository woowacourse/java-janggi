package janggi.dao.h2;

import janggi.dao.JdbcDataSource;
import janggi.dao.MoveDao;
import janggi.dao.entity.MoveEntity;
import janggi.domain.piece.PieceType;
import janggi.domain.side.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

public class H2MoveDao implements MoveDao {
    private final JdbcDataSource dataSource;

    public H2MoveDao(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(MoveEntity move) {
        String sql = "INSERT INTO MOVE (GAME_ID, PIECE_TYPE, SIDE, FROM_X, FROM_Y, TO_X, TO_Y) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, move.gameId());
            stmt.setString(2, move.pieceType().name());
            stmt.setString(3, move.side().name());
            stmt.setInt(4, move.fromX());
            stmt.setInt(5, move.fromY());
            stmt.setInt(6, move.toX());
            stmt.setInt(7, move.toY());
            stmt.executeUpdate();
        } catch (JdbcSQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException("게임 MoveNumber가 중복됩니다.");
        } catch (SQLException e) {
            throw new IllegalStateException("게임 ID " + move.gameId() + "의 이동 저장에 실패했습니다.", e);
        }
    }

    @Override
    public MoveEntity findById(int id) {
        String sql = "SELECT ID, GAME_ID, PIECE_TYPE, SIDE, FROM_X, FROM_Y, TO_X, TO_Y "
                + "FROM MOVE WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToMove(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException("이동 ID " + id + "의 조회에 실패했습니다.", e);
        }
    }

    @Override
    public List<MoveEntity> findByGameIdOrderByMoveNumber(int gameId) {
        String sql = "SELECT ID, GAME_ID, PIECE_TYPE, SIDE, FROM_X, FROM_Y, TO_X, TO_Y "
                + "FROM MOVE WHERE GAME_ID = ? ORDER BY ID";

        List<MoveEntity> moveEntities = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, gameId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                moveEntities.add(mapResultSetToMove(resultSet));
            }
            return moveEntities;
        } catch (SQLException e) {
            throw new IllegalStateException("게임 ID " + gameId + "의 이동 조회에 실패했습니다.", e);
        }
    }

    private MoveEntity mapResultSetToMove(ResultSet rs) throws SQLException {
        return new MoveEntity(
                rs.getInt("ID"),
                rs.getInt("GAME_ID"),
                PieceType.valueOf(rs.getString("PIECE_TYPE")),
                Side.valueOf(rs.getString("SIDE")),
                rs.getInt("FROM_X"),
                rs.getInt("FROM_Y"),
                rs.getInt("TO_X"),
                rs.getInt("TO_Y")
        );
    }
}
