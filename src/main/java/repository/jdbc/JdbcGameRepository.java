package repository.jdbc;

import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.PieceType;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import repository.GameRepository;
import repository.mapper.GameSnapshotMapper;
import repository.snapshot.GameSnapshot;
import repository.snapshot.GameStatus;
import repository.snapshot.PieceSnapshot;

public class JdbcGameRepository implements GameRepository {

    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;
    private static final int FOURTH_PARAMETER_INDEX = 4;
    private static final int FIFTH_PARAMETER_INDEX = 5;
    private static final int FIRST_GENERATED_KEY_INDEX = 1;
    
    private static final String ERROR_FIND_IN_PROGRESS_GAME = "진행 중인 게임 조회에 실패했습니다.";
    private static final String ERROR_FIND_GAME = "게임 조회에 실패했습니다.";
    private static final String ERROR_SAVE_GAME = "게임 저장에 실패했습니다.";
    private static final String ERROR_GAME_ID_NOT_FOUND = "생성된 game id를 찾을 수 없습니다.";
    private static final String ERROR_ROLLBACK_GAME = "게임 저장 롤백에 실패했습니다.";

    private static final String SELECT_IN_PROGRESS_GAME = """
            SELECT id, current_turn, status
            FROM games
            WHERE status = ?
            ORDER BY updated_at DESC, id DESC
            LIMIT 1
            """;
    private static final String SELECT_GAME = """
            SELECT id, current_turn, status
            FROM games
            WHERE id = ?
            """;
    private static final String SELECT_GAME_PIECES = """
            SELECT col_no, row_no, piece_type, team
            FROM game_pieces
            WHERE game_id = ?
            ORDER BY row_no, col_no
            """;
    private static final String INSERT_GAME = """
            INSERT INTO games (current_turn, status)
            VALUES (?, ?)
            """;
    private static final String UPDATE_GAME = """
            UPDATE games
            SET current_turn = ?, status = ?
            WHERE id = ?
            """;
    private static final String DELETE_GAME_PIECES = """
            DELETE FROM game_pieces
            WHERE game_id = ?
            """;
    private static final String INSERT_GAME_PIECE = """
            INSERT INTO game_pieces (game_id, col_no, row_no, piece_type, team)
            VALUES (?, ?, ?, ?, ?)
            """;

    private final ConnectionProvider connectionProvider;
    private final GameSnapshotMapper gameSnapshotMapper;

    public JdbcGameRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        this.gameSnapshotMapper = new GameSnapshotMapper();
    }

    @Override
    public Optional<Long> findInProgressGameId() {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_IN_PROGRESS_GAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, GameStatus.IN_PROGRESS.name());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_FIND_IN_PROGRESS_GAME, e);
        }
    }

    @Override
    public Optional<JanggiGame> findById(long gameId) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_GAME)) {
            statement.setLong(FIRST_PARAMETER_INDEX, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                Turn currentTurn = Turn.valueOf(resultSet.getString("current_turn"));
                GameStatus status = GameStatus.valueOf(resultSet.getString("status"));
                List<PieceSnapshot> pieces = findPieces(connection, gameId);

                return Optional.of(gameSnapshotMapper.toGame(new GameSnapshot(gameId, currentTurn, status, pieces)));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_FIND_GAME, e);
        }
    }

    @Override
    public long save(JanggiGame janggiGame) {
        return save(gameSnapshotMapper.from(null, janggiGame));
    }

    @Override
    public void save(long gameId, JanggiGame janggiGame) {
        save(gameSnapshotMapper.from(gameId, janggiGame));
    }

    private long save(GameSnapshot snapshot) {
        try (Connection connection = connectionProvider.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long gameId = saveGame(connection, snapshot);
                replacePieces(connection, gameId, snapshot.pieces());
                connection.commit();
                return gameId;
            } catch (SQLException e) {
                rollback(connection);
                throw new IllegalStateException(ERROR_SAVE_GAME, e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_SAVE_GAME, e);
        }
    }

    private List<PieceSnapshot> findPieces(Connection connection, long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GAME_PIECES)) {
            statement.setLong(FIRST_PARAMETER_INDEX, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<PieceSnapshot> pieces = new ArrayList<>();
                while (resultSet.next()) {
                    pieces.add(new PieceSnapshot(
                            resultSet.getInt("col_no"),
                            resultSet.getInt("row_no"),
                            PieceType.valueOf(resultSet.getString("piece_type")),
                            Team.valueOf(resultSet.getString("team"))
                    ));
                }
                return pieces;
            }
        }
    }

    private long saveGame(Connection connection, GameSnapshot snapshot) throws SQLException {
        if (snapshot.id() == null) {
            return insertGame(connection, snapshot);
        }
        updateGame(connection, snapshot);
        return snapshot.id();
    }

    private long insertGame(Connection connection, GameSnapshot snapshot) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, snapshot.currentTurn().name());
            statement.setString(SECOND_PARAMETER_INDEX, snapshot.status().name());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new IllegalStateException(ERROR_GAME_ID_NOT_FOUND);
                }
                return generatedKeys.getLong(FIRST_GENERATED_KEY_INDEX);
            }
        }
    }

    private void updateGame(Connection connection, GameSnapshot snapshot) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, snapshot.currentTurn().name());
            statement.setString(SECOND_PARAMETER_INDEX, snapshot.status().name());
            statement.setLong(THIRD_PARAMETER_INDEX, snapshot.id());
            statement.executeUpdate();
        }
    }

    private void replacePieces(Connection connection, long gameId, List<PieceSnapshot> pieces) throws SQLException {
        try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_GAME_PIECES)) {
            deleteStatement.setLong(FIRST_PARAMETER_INDEX, gameId);
            deleteStatement.executeUpdate();
        }

        try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_GAME_PIECE)) {
            for (PieceSnapshot piece : pieces) {
                insertStatement.setLong(FIRST_PARAMETER_INDEX, gameId);
                insertStatement.setInt(SECOND_PARAMETER_INDEX, piece.column());
                insertStatement.setInt(THIRD_PARAMETER_INDEX, piece.row());
                insertStatement.setString(FOURTH_PARAMETER_INDEX, piece.pieceType().name());
                insertStatement.setString(FIFTH_PARAMETER_INDEX, piece.team().name());
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            throw new IllegalStateException(ERROR_ROLLBACK_GAME, rollbackException);
        }
    }
}
