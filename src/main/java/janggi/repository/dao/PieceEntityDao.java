package janggi.repository.dao;

import janggi.model.position.absolute.Position;
import janggi.repository.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PieceEntityDao {

    private static final String PIECE_ID = "piece_id";
    private static final String GAME_ID = "game_id";
    private static final String PIECE_TYPE = "piece_type";
    private static final String POSITION_ROW = "position_row";
    private static final String POSITION_COLUMN = "position_column";
    private static final String TEAM = "team";

    public Long save(
            Connection con,
            Long gameId,
            String pieceType,
            int positionRow,
            int positionColumn,
            String team
    ) {
        String sql = """
        INSERT INTO piece(game_id, piece_type, position_row, position_column, team)      
        VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement psmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setLong(1, gameId);
            psmt.setString(2, pieceType);
            psmt.setLong(3, positionRow);
            psmt.setLong(4, positionColumn);
            psmt.setString(5, team);

            psmt.executeUpdate();

            return getGeneratedKey(psmt);
        } catch (SQLException e) {
            throw new IllegalStateException("piece 데이터 삽입에 실패했습니다.", e);
        }
    }

    private long getGeneratedKey(PreparedStatement psmt) throws SQLException {
        try(ResultSet rs = psmt.getGeneratedKeys()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    public List<PieceEntity> findAllByGameId(
            Connection con,
            long gameId
    ) {
        String sql = """
                SELECT *
                FROM piece
                WHERE game_id = (?)
                """;

        try (PreparedStatement psmt = con.prepareStatement(sql)){
            psmt.setLong(1, gameId);
            ResultSet rs = psmt.executeQuery();

            List<PieceEntity> result = new ArrayList<>();

            while (rs.next()) {
                result.add(getPieceEntityFrom(rs));
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException("기물 목록 조회에 실패했습니다.", e);
        }
    }

    public Optional<PieceEntity> findByPosition(
            Connection con,
            Position position
    ) {
        String sql = """
                SELECT *
                FROM piece
                WHERE position_row = (?) AND position_column = (?)
                """;

        try(PreparedStatement psmt = con.prepareStatement(sql)) {
            psmt.setInt(1, position.row().getValue());
            psmt.setInt(2, position.column().getValue());

            ResultSet rs = psmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(getPieceEntityFrom(rs));
        } catch (SQLException e) {
            throw new IllegalStateException("기물 조회에 실패했습니다.", e);
        }
    }

    private PieceEntity getPieceEntityFrom(ResultSet rs)
            throws SQLException {
        return new PieceEntity(
                rs.getLong(PIECE_ID),
                rs.getLong(GAME_ID),
                rs.getString(PIECE_TYPE),
                rs.getInt(POSITION_ROW),
                rs.getInt(POSITION_COLUMN),
                rs.getString(TEAM)
        );
    }

    public void updatePosition(
            Connection con,
            Long pieceId,
            Position to
    ) {
        String sql = """
                UPDATE piece
                SET position_row = (?), position_column = (?)
                WHERE piece_id = (?)
                """;

        try(PreparedStatement psmt = con.prepareStatement(sql)) {
            psmt.setInt(1, to.row().getValue());
            psmt.setInt(2, to.column().getValue());
            psmt.setLong(3, pieceId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("기물 조회에 실패했습니다.", e);
        }
    }
}
