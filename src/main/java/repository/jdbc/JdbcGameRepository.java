package repository.jdbc;

import domain.game.Turn;
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
import repository.snapshot.PieceType;
import service.LoadedGame;

public class JdbcGameRepository implements GameRepository {

    private static final String SELECT_IN_PROGRESS_GAME = """
            SELECT id, current_turn, status
            FROM games
            WHERE status = ?
            ORDER BY updated_at DESC, id DESC
            LIMIT 1
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
    public Optional<LoadedGame> findInProgressGame() {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_IN_PROGRESS_GAME)) {
            statement.setString(1, GameStatus.IN_PROGRESS.name());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                long gameId = resultSet.getLong("id");
                Turn currentTurn = Turn.valueOf(resultSet.getString("current_turn"));
                GameStatus status = GameStatus.valueOf(resultSet.getString("status"));
                List<PieceSnapshot> pieces = findPieces(connection, gameId);

                return Optional.of(
                        gameSnapshotMapper.toLoadedGame(new GameSnapshot(gameId, currentTurn, status, pieces))
                );
            }
        } catch (SQLException e) {
            throw new IllegalStateException("진행 중인 게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public LoadedGame save(LoadedGame loadedGame) {
        GameSnapshot snapshot = gameSnapshotMapper.from(loadedGame);
        try (Connection connection = connectionProvider.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long gameId = saveGame(connection, snapshot);
                replacePieces(connection, gameId, snapshot.pieces());
                connection.commit();
                return new LoadedGame(gameId, loadedGame.game());
            } catch (SQLException e) {
                rollback(connection);
                throw new IllegalStateException("게임 저장에 실패했습니다.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 저장에 실패했습니다.", e);
        }
    }

    private List<PieceSnapshot> findPieces(Connection connection, long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GAME_PIECES)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<PieceSnapshot> pieces = new ArrayList<>();
                while (resultSet.next()) {
                    pieces.add(new PieceSnapshot(
                            resultSet.getInt("col_no"),
                            resultSet.getInt("row_no"),
                            PieceType.valueOf(resultSet.getString("piece_type")),
                            domain.piece.Team.valueOf(resultSet.getString("team"))
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
            statement.setString(1, snapshot.currentTurn().name());
            statement.setString(2, snapshot.status().name());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new IllegalStateException("생성된 game id를 찾을 수 없습니다.");
                }
                return generatedKeys.getLong(1);
            }
        }
    }

    private void updateGame(Connection connection, GameSnapshot snapshot) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME)) {
            statement.setString(1, snapshot.currentTurn().name());
            statement.setString(2, snapshot.status().name());
            statement.setLong(3, snapshot.id());
            statement.executeUpdate();
        }
    }

    private void replacePieces(Connection connection, long gameId, List<PieceSnapshot> pieces) throws SQLException {
        try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_GAME_PIECES)) {
            deleteStatement.setLong(1, gameId);
            deleteStatement.executeUpdate();
        }

        try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_GAME_PIECE)) {
            for (PieceSnapshot piece : pieces) {
                insertStatement.setLong(1, gameId);
                insertStatement.setInt(2, piece.column());
                insertStatement.setInt(3, piece.row());
                insertStatement.setString(4, piece.pieceType().name());
                insertStatement.setString(5, piece.team().name());
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            throw new IllegalStateException("게임 저장 롤백에 실패했습니다.", rollbackException);
        }
    }
}
