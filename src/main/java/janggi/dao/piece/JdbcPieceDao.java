package janggi.dao.piece;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
            for (Entry<Position, Piece> entry : boardInfo.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                String team = Team.HAN.name();

                if (piece.isSameTeam(Team.CHO)) {
                    team = Team.CHO.name();
                }

                preparedStatement.setLong(1, gameId);
                preparedStatement.setString(2, piece.getPieceType().name());
                preparedStatement.setInt(3, position.row().getValue());
                preparedStatement.setInt(4, position.column().getValue());
                preparedStatement.setString(5, team);

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("대규모 piece 데이터 삽입에 실패했습니다.", e);
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            ResultSet rs = preparedStatement.executeQuery();

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

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, position.row().getValue());
            preparedStatement.setInt(2, position.column().getValue());

            ResultSet rs = preparedStatement.executeQuery();

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
    public void updatePosition(
            Connection connection,
            Long pieceId,
            Position to
    ) {
        String sql = """
                UPDATE piece
                SET position_row = (?), position_column = (?)
                WHERE piece_id = (?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, to.row().getValue());
            preparedStatement.setInt(2, to.column().getValue());
            preparedStatement.setLong(3, pieceId);

            preparedStatement.executeUpdate();
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, position.row().getValue());
            preparedStatement.setInt(2, position.column().getValue());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
