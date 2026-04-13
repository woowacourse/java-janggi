package repository.jdbc;

import domain.board.Board;
import domain.piece.PieceRepository;
import repository.entity.PieceEntity;
import repository.mapper.PieceMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPieceRepository implements PieceRepository {

    private static final String FIND_BY_GAME_ID = """
            SELECT piece_id, game_id, team, piece_type, move_strategy_type, board_row, board_column
            FROM piece
            WHERE game_id = ?
            ORDER BY piece_id
            """;

    private static final String INSERT = """
            INSERT INTO piece (game_id, team, piece_type, move_strategy_type, board_row, board_column)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String DELETE_ALL_BY_GAME_ID = """
            DELETE FROM piece
            WHERE game_id = ?
            """;

    private static final String FIND_PIECE_FAILED = "기물 조회에 실패했습니다.";
    private static final String SAVE_PIECE_FAILED = "기물 저장에 실패했습니다.";
    private static final String DELETE_PIECE_FAILED = "기물 전체 삭제에 실패했습니다.";

    private final PieceMapper pieceMapper;

    public JdbcPieceRepository() {
        this.pieceMapper = new PieceMapper();
    }

    @Override
    public Board findByGameId(final Connection connection, final long gameId) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_GAME_ID)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return pieceMapper.toBoard(toEntities(resultSet));
            }
        } catch (final SQLException exception) {
            throw new RuntimeException(FIND_PIECE_FAILED, exception);
        }
    }

    @Override
    public void saveAll(
            final Connection connection,
            final long gameId,
            final Board board
    ) {
        final List<PieceEntity> pieceEntities = pieceMapper.toEntities(gameId, board);

        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            for (final PieceEntity pieceEntity : pieceEntities) {
                statement.setLong(1, pieceEntity.getGameId());
                statement.setString(2, pieceEntity.getTeam());
                statement.setString(3, pieceEntity.getPieceType());
                statement.setString(4, pieceEntity.getMoveStrategyType());
                statement.setInt(5, pieceEntity.getBoardRow());
                statement.setInt(6, pieceEntity.getBoardColumn());
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (final SQLException exception) {
            throw new RuntimeException(SAVE_PIECE_FAILED, exception);
        }
    }

    @Override
    public void deleteAllByGameId(final Connection connection, final long gameId) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_BY_GAME_ID)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw new RuntimeException(DELETE_PIECE_FAILED, exception);
        }
    }

    private List<PieceEntity> toEntities(final ResultSet resultSet) throws SQLException {
        final List<PieceEntity> pieceEntities = new ArrayList<>();

        while (resultSet.next()) {
            pieceEntities.add(toEntity(resultSet));
        }

        return pieceEntities;
    }

    private PieceEntity toEntity(final ResultSet resultSet) throws SQLException {
        return new PieceEntity(
                resultSet.getLong("piece_id"),
                resultSet.getLong("game_id"),
                resultSet.getString("team"),
                resultSet.getString("piece_type"),
                resultSet.getString("move_strategy_type"),
                resultSet.getInt("board_row"),
                resultSet.getInt("board_column")
        );
    }
}
