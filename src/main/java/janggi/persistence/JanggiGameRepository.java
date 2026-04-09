package janggi.persistence;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.game.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.dto.GameSessionDTO;
import janggi.dto.PiecePositionSnapshot;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiGameRepository implements GameRepository {

    @Override
    public List<GameSessionDTO> findAllGameStatusByFinishedFalse(Connection connection) throws SQLException {
        String sql = "select game_id, cho_player_name, han_player_name, current_turn, created_at " +
                "from game where is_finished = false order by created_at desc";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return executeFindAllActiveGames(statement);
        }
    }

    private List<GameSessionDTO> executeFindAllActiveGames(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            return mapToGameSessionList(resultSet);
        }
    }

    private List<GameSessionDTO> mapToGameSessionList(ResultSet resultSet) throws SQLException {
        List<GameSessionDTO> sessions = new ArrayList<>();
        while (resultSet.next()) {
            sessions.add(mapToSingleSession(resultSet));
        }
        return sessions;
    }

    private GameSessionDTO mapToSingleSession(ResultSet resultSet) throws SQLException {
        return new GameSessionDTO(
                resultSet.getLong("game_id"),
                resultSet.getString("cho_player_name"),
                resultSet.getString("han_player_name"),
                resultSet.getString("current_turn"),
                resultSet.getObject("created_at", LocalDateTime.class)
        );
    }

    @Override
    public long save(Connection connection, GameManager gameManager) throws SQLException {
        long gameId = saveGame(connection, gameManager);
        saveBoard(connection, gameId, gameManager.getBoard());
        return gameId;
    }

    private long saveGame(Connection connection, GameManager gameManager) throws SQLException {
        if (gameManager.getId() == null) {
            return insertGame(connection, gameManager);
        }
        return updateGame(connection, gameManager);
    }

    private long updateGame(Connection connection, GameManager gameManager) throws SQLException {
        String sql = "update game set cho_player_name = ?, han_player_name = ?, current_turn = ? where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(4, gameManager.getId());
            return executeInsertAndGetId(statement, gameManager);
        }
    }

    private long insertGame(Connection connection, GameManager gameManager) throws SQLException {
        String sql = "insert into game (cho_player_name, han_player_name, current_turn) values (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            return executeInsertAndGetId(statement, gameManager);
        }
    }

    private long executeInsertAndGetId(PreparedStatement statement, GameManager gameManager) throws SQLException {
        bindInsertParameters(statement, gameManager);
        statement.executeUpdate();
        return extractGeneratedId(statement);
    }

    private void bindInsertParameters(PreparedStatement statement, GameManager gameManager) throws SQLException {
        Map<Side, String> playersInfo = gameManager.getPlayersInfo();
        String choPlayerName = playersInfo.get(Side.CHO);
        String hanPlayerName = playersInfo.get(Side.HAN);
        String currentTurnName = gameManager.currentPlayer().side().name();
        statement.setString(1, choPlayerName);
        statement.setString(2, hanPlayerName);
        statement.setString(3, currentTurnName);
    }

    private long extractGeneratedId(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            return mapToGeneratedId(resultSet);
        }
    }

    private long mapToGeneratedId(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new SQLException("생성된 게임의 ID가 조회되지 않습니다.");
        }
        return resultSet.getLong(1);
    }

    @Override
    public GameManager findByGameId(Connection connection, long gameId) throws SQLException {
        GameSessionDTO gameInfo = findGameInfoById(connection, gameId);
        Board board = findAllPieceByGameId(connection, gameId);
        return generateExistingGameManager(gameInfo, board);
    }

    private GameSessionDTO findGameInfoById(Connection connection, long gameId) throws SQLException {
        String sql = "select game_id, cho_player_name, han_player_name, current_turn, created_at from game where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return executeFindById(statement, gameId);
        }
    }

    private GameSessionDTO executeFindById(PreparedStatement statement, long gameId) throws SQLException {
        statement.setLong(1, gameId);
        try (ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return mapToGameSessionDTO(resultSet);
        }
    }

    private GameSessionDTO mapToGameSessionDTO(ResultSet resultSet) throws SQLException {
        long gameId = resultSet.getLong("game_id");
        String choPlayerName = resultSet.getString("cho_player_name");
        String hanPlayerName = resultSet.getString("han_player_name");
        String currentTurnName = resultSet.getString("current_turn");
        LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);
        return new GameSessionDTO(gameId, choPlayerName, hanPlayerName, currentTurnName, createdAt);
    }

    public Board findAllPieceByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "select side, piece_type, piece_number, row_index, column_index from board where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return executeFindAllById(statement, gameId);
        }
    }

    private Board executeFindAllById(PreparedStatement statement, long gameId) throws SQLException {
        statement.setLong(1, gameId);
        try (ResultSet resultSet = statement.executeQuery()) {
            return mapToBoard(resultSet);
        }
    }

    private Board mapToBoard(ResultSet resultSet) throws SQLException {
        Map<Position, Piece> board = new HashMap<>();
        while (resultSet.next()) {
            mapToSinglePiecePosition(resultSet, board);
        }
        return new Board(board);
    }

    private void mapToSinglePiecePosition(ResultSet resultSet, Map<Position, Piece> board) throws SQLException {
        Side side = Side.valueOf(resultSet.getString("side"));
        PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
        String pieceNumber = resultSet.getString("piece_number");
        Piece piece = new Piece(side, pieceType, pieceNumber);

        int rowIndex = resultSet.getInt("row_index");
        int columnIndex = resultSet.getInt("column_index");
        Position position = new Position(rowIndex, columnIndex);
        board.put(position, piece);
    }

    private GameManager generateExistingGameManager(GameSessionDTO gameInfo, Board board) {
        long gameId = gameInfo.gameId();
        Turn currentTurn = new Turn(Side.valueOf(gameInfo.currentTurn()));
        Players players = Players.fromCurrentTurn(gameInfo.choPlayerName(), gameInfo.hanPlayerName(), currentTurn);
        return GameManager.loadGame(players, board, gameId);
    }

    public void saveBoard(Connection connection, long gameId, Board board)
            throws SQLException {
        deleteAllPiecesByGameId(connection, gameId);
        insertAllPiecesByGameId(connection, gameId, board);
    }

    private void deleteAllPiecesByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "delete from board where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }

    private void insertAllPiecesByGameId(Connection connection, long gameId, Board board) throws SQLException {
        String sql = "insert into board (game_id, side, piece_type, piece_number, row_index, column_index) values (?, ?, ?, ?, ?, ?)";
        List<PiecePositionSnapshot> snapshots = mapToSnapshots(board.piecePosition());
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            executeBatchInsert(statement, gameId, snapshots);
        }
    }

    private List<PiecePositionSnapshot> mapToSnapshots(Map<Position, Piece> piecePosition) {
        return piecePosition.entrySet().stream()
                .map(this::createSnapshot)
                .toList();
    }

    private PiecePositionSnapshot createSnapshot(Map.Entry<Position, Piece> entry) {
        Position position = entry.getKey();
        Piece piece = entry.getValue();
        return new PiecePositionSnapshot(
                piece.side().name(),
                piece.type().name(),
                piece.pieceNumber(),
                position.row(),
                position.column()
        );
    }

    private void executeBatchInsert(PreparedStatement statement, long gameId, List<PiecePositionSnapshot> snapshots)
            throws SQLException {
        for (PiecePositionSnapshot snapshot : snapshots) {
            bindPieceParameters(statement, gameId, snapshot);
            statement.addBatch();
        }
        statement.executeBatch();
    }

    private void bindPieceParameters(PreparedStatement statement, long gameId, PiecePositionSnapshot snapshot)
            throws SQLException {
        statement.setLong(1, gameId);
        statement.setString(2, snapshot.side());
        statement.setString(3, snapshot.pieceType());
        statement.setString(4, snapshot.pieceNumber());
        statement.setInt(5, snapshot.rowIndex());
        statement.setInt(6, snapshot.columnIndex());
    }

    private void executeAndValidateUpdate(PreparedStatement statement) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("업데이트 대상 게임을 찾을 수 없습니다.");
        }
    }
}
