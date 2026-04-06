package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public void save(
            GameSnapshot snapshot,
            TeamColor currentTurn,
            GameStatus gameStatus,
            Optional<TeamColor> winner) {
        try {
            saveInternal(snapshot, currentTurn, gameStatus, winner);
        } catch (SQLException exception) {
            throw new IllegalStateException("게임 상태 저장에 실패했습니다.", exception);
        }
    }

    private Optional<SavedGameState> loadInternal() throws SQLException {
        Connection connection = connectionSource.getConnection();
        Optional<GameMetaRow> meta = readMeta(connection);
        if (meta.isEmpty()) {
            return Optional.empty();
        }
        return buildStateIfBoardPresent(connection, meta.get());
    }

    private Optional<SavedGameState> buildStateIfBoardPresent(Connection connection, GameMetaRow meta)
            throws SQLException {
        GameSnapshot snapshot = readSnapshot(connection);
        if (snapshot.pieces().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new SavedGameState(snapshot, meta.currentTurn(), meta.gameStatus(), meta.winner()));
    }

    private Optional<GameMetaRow> readMeta(Connection connection) throws SQLException {
        String sql = "SELECT current_turn, game_status, winner_team FROM game_meta WHERE id = ?";
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
            Optional<TeamColor> winner =
                    Optional.ofNullable(resultSet.getString("winner_team")).map(TeamColor::valueOf);
            return Optional.of(new GameMetaRow(currentTurn, status, winner));
        }
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

    private void saveInternal(
            GameSnapshot snapshot,
            TeamColor currentTurn,
            GameStatus gameStatus,
            Optional<TeamColor> winner)
            throws SQLException {
        Connection connection = connectionSource.getConnection();
        connection.setAutoCommit(false);
        commitOrRollback(connection, snapshot, currentTurn, gameStatus, winner);
        connection.setAutoCommit(true);
    }

    private static void commitOrRollback(
            Connection connection,
            GameSnapshot snapshot,
            TeamColor turn,
            GameStatus status,
            Optional<TeamColor> winner)
            throws SQLException {
        try {
            writeGameState(connection, snapshot, turn, status, winner);
            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();
            throw exception;
        }
    }

    private static void writeGameState(
            Connection connection,
            GameSnapshot snapshot,
            TeamColor turn,
            GameStatus status,
            Optional<TeamColor> winner)
            throws SQLException {
        deleteAllPieces(connection);
        insertPieces(connection, snapshot);
        mergeMeta(connection, turn, status, winner);
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

    private static void mergeMeta(
            Connection connection, TeamColor currentTurn, GameStatus status, Optional<TeamColor> winner)
            throws SQLException {
        String sql =
                "MERGE INTO game_meta (id, current_turn, game_status, winner_team) KEY (id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, SINGLETON_GAME_ID);
            statement.setString(2, currentTurn.name());
            statement.setString(3, status.name());
            statement.setString(4, winner.map(Enum::name).orElse(null));
            statement.executeUpdate();
        }
    }

    private record GameMetaRow(TeamColor currentTurn, GameStatus gameStatus, Optional<TeamColor> winner) {}
}
