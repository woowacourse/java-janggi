package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import domain.GameDeadline;
import domain.GameSnapshot;
import domain.GameStatus;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;

public final class JdbcGameStateRepository implements GameStateRepository {

    private static final int SINGLETON_GAME_ID = 1;

    private final SqlConnectionSource connectionSource;

    public JdbcGameStateRepository(SqlConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public Optional<SavedGameState> load() {
        try {
            return loadInternal();
        } catch (SQLException exception) {
            throw new IllegalStateException("게임 상태를 불러오지 못했습니다.", exception);
        }
    }

    @Override
    public void save(SaveGameStateRequest command) {
        try {
            saveInternal(command);
        } catch (Exception exception) {
            throw new IllegalStateException("게임 상태 저장에 실패했습니다.", exception);
        }
    }

    private Optional<SavedGameState> loadInternal() throws SQLException {
        try (Connection connection = connectionSource.getConnection()) {
            Optional<GameMetaRow> meta = readMeta(connection);
            if (meta.isEmpty()) {
                return Optional.empty();
            }
            return buildStateIfBoardPresent(connection, meta.get());
        }
    }

    private Optional<SavedGameState> buildStateIfBoardPresent(Connection connection, GameMetaRow meta)
            throws SQLException {
        GameSnapshot snapshot = readSnapshot(connection);
        if (snapshot.pieces().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new SavedGameState(
                        snapshot, meta.currentTurn(), meta.gameStatus(), meta.winner(), meta.deadline()));
    }

    private Optional<GameMetaRow> readMeta(Connection connection) throws SQLException {
        String sql =
                "SELECT current_turn, game_status, winner_team, deadline_epoch_ms FROM game_meta WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, SINGLETON_GAME_ID);
            return queryMeta(statement);
        }
    }

    private static Optional<GameMetaRow> queryMeta(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return Optional.empty();
            }
            TeamColor currentTurn = TeamColor.valueOf(resultSet.getString("current_turn"));
            GameStatus status = GameStatus.valueOf(resultSet.getString("game_status"));
            TeamColor winner = readWinner(resultSet);
            GameDeadline deadline = readDeadline(resultSet);
            return Optional.of(new GameMetaRow(currentTurn, status, winner, deadline));
        }
    }

    private static TeamColor readWinner(ResultSet resultSet) throws SQLException {
        String winnerTeam = resultSet.getString("winner_team");
        if (winnerTeam == null) {
            return null;
        }
        return TeamColor.valueOf(winnerTeam);
    }

    private static GameDeadline readDeadline(ResultSet resultSet) throws SQLException {
        Long epochMillis = (Long) resultSet.getObject("deadline_epoch_ms");
        if (epochMillis == null) {
            return null;
        }
        return GameDeadline.of(Instant.ofEpochMilli(epochMillis));
    }

    private static GameSnapshot readSnapshot(Connection connection) throws SQLException {
        String sql = "SELECT cell_row, cell_col, team, piece_type FROM board_cell";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            return GameSnapshot.from(readPieces(resultSet));
        }
    }

    private static Map<Position, Piece> readPieces(ResultSet resultSet) throws SQLException {
        Map<Position, Piece> pieces = new HashMap<>();
        while (resultSet.next()) {
            putRow(pieces, resultSet);
        }
        return pieces;
    }

    private static void putRow(Map<Position, Piece> pieces, ResultSet resultSet) throws SQLException {
        Position position = Position.of(resultSet.getInt("cell_row"), resultSet.getInt("cell_col"));
        TeamColor team = TeamColor.valueOf(resultSet.getString("team"));
        Piece piece = Piece.of(team, PieceType.valueOf(resultSet.getString("piece_type")));
        pieces.put(position, piece);
    }

    private void saveInternal(SaveGameStateRequest command) throws Exception {
        try (Connection connection = connectionSource.getConnection()) {
            boolean originalAutoCommit = connection.getAutoCommit();
            beginTransaction(connection);
            try {
                writeGameState(connection, command);
                connection.commit();
            } catch (Exception exception) {
                rollbackAndSuppressIfFailed(connection, exception);
                throw exception;
            } finally {
                restoreAutoCommit(connection, originalAutoCommit);
            }
        }
    }

    private static void beginTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }

    private static void rollbackAndSuppressIfFailed(Connection connection, Exception cause) {
        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            cause.addSuppressed(rollbackException);
        }
    }

    private static void restoreAutoCommit(Connection connection, boolean originalAutoCommit) {
        try {
            connection.setAutoCommit(originalAutoCommit);
        } catch (SQLException exception) {
            throw new IllegalStateException("커넥션 설정 복구에 실패했습니다.", exception);
        }
    }

    private static void writeGameState(Connection connection, SaveGameStateRequest command)
            throws SQLException {
        deleteAllPieces(connection);
        insertPieces(connection, command.snapshot());
        mergeMeta(connection, command);
    }

    private static void deleteAllPieces(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM board_cell");
        }
    }

    private static void insertPieces(Connection connection, GameSnapshot snapshot) throws SQLException {
        String sql =
                "INSERT INTO board_cell (cell_row, cell_col, team, piece_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            appendPieces(statement, snapshot);
        }
    }

    private static void appendPieces(PreparedStatement statement, GameSnapshot snapshot)
            throws SQLException {
        for (Map.Entry<Position, Piece> entry : snapshot.pieces().entrySet()) {
            bindPiece(statement, entry);
            statement.executeUpdate();
        }
    }

    private static void bindPiece(PreparedStatement statement, Map.Entry<Position, Piece> entry)
            throws SQLException {
        Position position = entry.getKey();
        Piece piece = entry.getValue();
        statement.setInt(1, position.row());
        statement.setInt(2, position.column());
        statement.setString(3, piece.getTeamColor().name());
        statement.setString(4, piece.getPieceType().name());
    }

    private static void mergeMeta(Connection connection, SaveGameStateRequest command)
            throws SQLException {
        String sql =
                "MERGE INTO game_meta (id, current_turn, game_status, winner_team, deadline_epoch_ms) KEY (id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, SINGLETON_GAME_ID);
            statement.setString(2, command.currentTurn().name());
            statement.setString(3, command.gameStatus().name());
            statement.setString(4, teamNameOrNull(command.winner()));
            statement.setObject(5, deadlineEpochMillisOrNull(command.deadline()));
            statement.executeUpdate();
        }
    }

    private static String teamNameOrNull(TeamColor winner) {
        if (winner == null) {
            return null;
        }
        return winner.name();
    }

    private static Long deadlineEpochMillisOrNull(GameDeadline deadline) {
        if (deadline == null) {
            return null;
        }
        return toEpochMillis(deadline);
    }

    private static long toEpochMillis(GameDeadline deadline) {
        return deadline.instant().toEpochMilli();
    }

    private record GameMetaRow(
            TeamColor currentTurn,
            GameStatus gameStatus,
            TeamColor winner,
            GameDeadline deadline) {}
}
