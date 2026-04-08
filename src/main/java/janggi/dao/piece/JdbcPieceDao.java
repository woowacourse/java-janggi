package janggi.dao.piece;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class JdbcPieceDao implements PieceDao {

    private static final String PIECE_ID = "piece_id";
    private static final String GAME_ID = "game_id";
    private static final String PIECE_TYPE = "piece_type";
    private static final String POSITION_ROW = "position_row";
    private static final String POSITION_COLUMN = "position_column";
    private static final String TEAM = "team";

    private static final String INSERT_SQL = """
            INSERT INTO piece(game_id, piece_type, position_row, position_column, team)      
            VALUES (?, ?, ?, ?, ?)
            """;

    @Override
    public void saveBoard(
            Connection connection,
            Map<Position, Piece> boardInfo,
            Long gameId
    ) {
        try (PreparedStatement psmt = connection.prepareStatement(INSERT_SQL)) {
            for (Entry<Position, Piece> entry : boardInfo.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                String team = Team.HAN.name();

                if (piece.isSameTeam(Team.CHO)) {
                    team = Team.CHO.name();
                }

                psmt.setLong(1, gameId);
                psmt.setString(2, piece.getPieceType().name());
                psmt.setInt(3, position.row().getValue());
                psmt.setInt(4, position.column().getValue());
                psmt.setString(5, team);

                psmt.addBatch();
            }

            psmt.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("대규모 piece 데이터 삽입에 실패했습니다.", e);
        }
    }

    @Override
    public Long savePiece(
            Connection connection,
            Long gameId,
            String pieceType,
            int positionRow,
            int positionColumn,
            String team
    ) {
        try (PreparedStatement psmt = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
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
        try (ResultSet rs = psmt.getGeneratedKeys()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    @Override
    public List<PieceEntity> findAllPiecesByGameId(
            Connection connection,
            long gameId
    ) {
        String sql = """
                SELECT *
                FROM piece
                WHERE game_id = (?)
                """;

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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

    @Override
    public Optional<PieceEntity> findPieceByPosition(
            Connection connection,
            Position position
    ) {
        String sql = """
                SELECT *
                FROM piece
                WHERE position_row = (?) AND position_column = (?)
                """;

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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

    @Override
    public void updatePieceOfPosition(
            Connection connection,
            Long pieceId,
            Position to
    ) {
        String sql = """
                UPDATE piece
                SET position_row = (?), position_column = (?)
                WHERE piece_id = (?)
                """;

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, to.row().getValue());
            psmt.setInt(2, to.column().getValue());
            psmt.setLong(3, pieceId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("기물 업데이트에 실패했습니다.", e);
        }
    }

    @Override
    public void deletePieceByPosition(
            Connection connection,
            Position position
    ) {
        String sql = """
                DELETE FROM piece
                WHERE position_row = (?) AND position_column = (?)
                """;

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, position.row().getValue());
            psmt.setInt(2, position.column().getValue());

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
