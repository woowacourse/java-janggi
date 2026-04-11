package janggi.dao.h2;

import janggi.dao.BoardDao;
import janggi.dao.ConnectionHolder;
import janggi.dao.entity.BoardEntity;
import janggi.dao.entity.MoveEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2BoardDao implements BoardDao {

    @Override
    public void save(int gameId, MoveEntity move) {
        String insertSql = """
                MERGE INTO BOARD (GAME_ID, PIECE_ID, SIDE, X, Y, UPDATED_AT)
                KEY (GAME_ID, X, Y)
                VALUES (
                    ?,
                    (SELECT id FROM PIECE WHERE PIECE_TYPE = ?),
                    ?,
                    ?,
                    ?,
                    CURRENT_TIMESTAMP
                )
                """;
        Connection conn = ConnectionHolder.get();
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            insertStmt.setInt(1, gameId);
            insertStmt.setString(2, move.pieceType());
            insertStmt.setString(3, move.side());
            insertStmt.setInt(4, move.x());
            insertStmt.setInt(5, move.y());

            insertStmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "MOVE 저장에 실패했습니다. gameId: " + gameId, e);
        }
    }

    @Override
    public void save(BoardEntity boardEntity) {
        List<MoveEntity> moveEntities = boardEntity.moveEntities();

        moveEntities.forEach(moveEntity -> this.save(boardEntity.gameId(), moveEntity));
    }

    @Override
    public List<MoveEntity> findAllByGameId(int gameId) {
        String sql = """
                SELECT b.id, p.piece_type, b.side, b.x, b.y
                FROM BOARD b
                JOIN PIECE p
                ON b.PIECE_ID = p.ID
                WHERE b.GAME_ID = ?
                """;
        Connection conn = ConnectionHolder.get();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();

            List<MoveEntity> moveEntities = new ArrayList<>();

            while (rs.next()) {
                MoveEntity moveEntity = new MoveEntity(
                        rs.getInt("id"),
                        rs.getString("piece_type"),
                        rs.getString("side"),
                        rs.getInt("x"),
                        rs.getInt("y")
                );

                moveEntities.add(moveEntity);
            }

            return moveEntities;

        } catch (SQLException e) {
            throw new IllegalStateException("Board 찾기에 실패했습니다. board.gameId: " + gameId, e);
        }
    }

    @Override
    public void delete(int gameId, int x, int y) {
        String sql = """
                DELETE FROM BOARD
                WHERE GAME_ID =? AND X=? AND Y=?
                """;
        Connection conn = ConnectionHolder.get();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, gameId);
            stmt.setInt(2, x);
            stmt.setInt(3, y);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "MOVE 삭제에 실패했습니다. gameId: " + gameId, e);
        }
    }
}
