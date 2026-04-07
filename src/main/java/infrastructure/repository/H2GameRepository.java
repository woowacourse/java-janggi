package infrastructure.repository;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.setup.Arrangement;
import domain.state.GameStateName;
import infrastructure.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class H2GameRepository implements GameRepository {

    private final DatabaseManager databaseManager;

    public H2GameRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public long save(long roomId) {
        String sql = "INSERT INTO games (room_id) VALUES (?)";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, roomId);
            statement.executeUpdate();
            return extractGeneratedKey(statement);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 생성 실패: " + e.getMessage(), e);
        }
    }

    private long extractGeneratedKey(PreparedStatement statement) throws SQLException {
        try (ResultSet keys = statement.getGeneratedKeys()) {
            keys.next();
            return keys.getLong(1);
        }
    }

    @Override
    public void updateState(long gameId, GameStateName stateName, Team currentTeam) {
        String sql = "UPDATE games SET current_state = ?, current_team = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, stateName.name());
            statement.setString(2, currentTeam.name());
            statement.setLong(3, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 상태 저장 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateArrangement(long gameId, Team team, Arrangement arrangement) {
        String column = columnName(team);
        String sql = "UPDATE games SET " + column + " = ? WHERE id = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, arrangement.name());
            statement.setLong(2, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 상차림 저장 실패: " + e.getMessage(), e);
        }
    }

    private String columnName(Team team) {
        if (team == Team.HAN) {
            return "han_arrangement";
        }
        return "cho_arrangement";
    }

    @Override
    public void updateBoard(long gameId, Map<Position, Piece> pieces) {
        deleteBoard(gameId);
        insertBoard(gameId, pieces);
    }

    private void deleteBoard(long gameId) {
        String sql = "DELETE FROM board_pieces WHERE game_id = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 초기화 실패: " + e.getMessage(), e);
        }
    }

    private void insertBoard(long gameId, Map<Position, Piece> pieces) {
        String sql = "INSERT INTO board_pieces (game_id, position, team, piece_type) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            addAllPiecesToBatch(statement, gameId, pieces);
            statement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 저장 실패: " + e.getMessage(), e);
        }
    }

    private void addAllPiecesToBatch(PreparedStatement statement, long gameId, Map<Position, Piece> pieces) throws SQLException {
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            addPieceToBatch(statement, gameId, entry);
        }
    }

    private void addPieceToBatch(PreparedStatement statement, long gameId, Map.Entry<Position, Piece> entry) throws SQLException {
        Position position = entry.getKey();
        Piece piece = entry.getValue();
        Team team = piece.getTeam();
        PieceType pieceType = piece.getPieceType();
        statement.setLong(1, gameId);
        statement.setString(2, encodePosition(position));
        statement.setString(3, team.name());
        statement.setString(4, pieceType.name());
        statement.addBatch();
    }

    @Override
    public Optional<GameDto> findLatestByRoom(long roomId) {
        String sql = "SELECT id, current_state, current_team, han_arrangement, cho_arrangement " +
                     "FROM games WHERE room_id = ? ORDER BY id DESC LIMIT 1";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, roomId);
            return querySnapshot(statement);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 복원 실패: " + e.getMessage(), e);
        }
    }

    private Optional<GameDto> querySnapshot(PreparedStatement statement) throws SQLException {
        try (ResultSet result = statement.executeQuery()) {
            return mapSnapshot(result);
        }
    }

    private Optional<GameDto> mapSnapshot(ResultSet result) throws SQLException {
        if (!result.next()) {
            return Optional.empty();
        }
        return Optional.of(toGameDto(result));
    }

    private GameDto toGameDto(ResultSet result) throws SQLException {
        long gameId = result.getLong("id");
        String stateName = result.getString("current_state");
        Team currentTeam = Team.valueOf(result.getString("current_team"));
        Optional<Arrangement> hanArrangement = parseArrangement(result.getString("han_arrangement"));
        Optional<Arrangement> choArrangement = parseArrangement(result.getString("cho_arrangement"));
        Map<Position, Piece> pieces = loadBoardPieces(gameId);
        return new GameDto(gameId, stateName, currentTeam, hanArrangement, choArrangement, pieces);
    }

    private Map<Position, Piece> loadBoardPieces(long gameId) throws SQLException {
        String sql = "SELECT position, team, piece_type FROM board_pieces WHERE game_id = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            return queryPieces(statement);
        }
    }

    private Map<Position, Piece> queryPieces(PreparedStatement statement) throws SQLException {
        try (ResultSet result = statement.executeQuery()) {
            return collectPieces(result);
        }
    }

    private Map<Position, Piece> collectPieces(ResultSet result) throws SQLException {
        Map<Position, Piece> pieces = new HashMap<>();
        while (result.next()) {
            addPiece(pieces, result);
        }
        return pieces;
    }

    private void addPiece(Map<Position, Piece> pieces, ResultSet result) throws SQLException {
        Position position = decodePosition(result.getString("position"));
        Team team = Team.valueOf(result.getString("team"));
        PieceType pieceType = PieceType.valueOf(result.getString("piece_type"));
        pieces.put(position, new Piece(team, pieceType));
    }

    private String encodePosition(Position position) {
        Column column = position.column();
        Row row = position.row();
        return column.name() + row.display();
    }

    private Position decodePosition(String encoded) {
        Column column = Column.valueOf(String.valueOf(encoded.charAt(0)));
        Row row = Row.toRow(encoded.charAt(1));
        return new Position(column, row);
    }

    private Optional<Arrangement> parseArrangement(String value) {
        return Optional.ofNullable(value).map(Arrangement::valueOf);
    }
}
