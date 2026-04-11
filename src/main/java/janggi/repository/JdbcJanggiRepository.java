package janggi.repository;

import janggi.domain.Camp;
import janggi.domain.Janggi;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.state.GameState;
import janggi.domain.state.GameStateFactory;

import java.sql.*;
import java.util.*;

public class JdbcJanggiRepository implements JanggiRepository {

    @Override
    public Long save(Janggi janggi) {
        String insertGameSql = "INSERT INTO game (turn, ongoing, state_type) VALUES (?, ?, ?)";
        try (Connection connection = DBConnectionProvider.getConnection()) {
            return executeInSaveTransaction(connection, insertGameSql, janggi);
        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 저장 중 오류 발생");
        }
    }

    private Long executeInSaveTransaction(Connection connection, String sql, Janggi janggi) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            Long gameId = executeGameInsert(connection, statement, janggi);
            connection.commit();
            return gameId;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private Long executeGameInsert(Connection connection, PreparedStatement statement, Janggi janggi) throws SQLException {
        statement.setString(1, janggi.currentTurn().name());
        statement.setBoolean(2, janggi.isOnGoing());
        statement.setString(3, janggi.getStateType());
        statement.executeUpdate();
        return extractGameIdAndSavePieces(connection, statement, janggi);
    }

    private Long extractGameIdAndSavePieces(Connection connection, PreparedStatement statement, Janggi janggi) throws SQLException {
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            return processGeneratedKey(connection, resultSet, janggi);
        }
    }

    private Long processGeneratedKey(Connection connection, ResultSet resultSet, Janggi janggi) throws SQLException {
        if (resultSet.next()) {
            Long gameId = resultSet.getLong(1);
            savePieces(connection, gameId, janggi);
            return gameId;
        }
        throw new SQLException("게임 ID 생성 실패");
    }

    private void savePieces(Connection connection, Long gameId, Janggi janggi) throws SQLException {
        String insertPieceSql = "INSERT INTO piece (game_id, camp, piece_type, row_pos, col_pos) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertPieceSql)) {
            executePieceBatch(statement, gameId, janggi.getBoardSnapshot());
        }
    }

    private void executePieceBatch(PreparedStatement statement, Long gameId, Map<Position, Piece> snapshot) throws SQLException {
        for (Map.Entry<Position, Piece> entry : snapshot.entrySet()) {
            addSinglePieceToBatch(statement, gameId, entry.getKey(), entry.getValue());
        }
        statement.executeBatch();
    }

    private void addSinglePieceToBatch(PreparedStatement statement, Long gameId, Position position, Piece piece) throws SQLException {
        statement.setLong(1, gameId);
        statement.setString(2, piece.getCamp().name());
        statement.setString(3, piece.getPieceType());
        statement.setInt(4, position.getRow());
        statement.setInt(5, position.getColumn());
        statement.addBatch();
    }

    @Override
    public void update(Long id, Janggi janggi) {
        try (Connection connection = DBConnectionProvider.getConnection()) {
            executeInTransaction(connection, id, janggi);
        } catch (SQLException e) {
            throw new RuntimeException("게임 업데이트 중 오류 발생", e);
        }
    }

    private void executeInTransaction(Connection connection, Long id, Janggi janggi) throws SQLException {
        connection.setAutoCommit(false);
        try {
            performUpdateSteps(connection, id, janggi);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void performUpdateSteps(Connection connection, Long id, Janggi janggi) throws SQLException {
        updateGameRecord(connection, id, janggi);
        deleteAllPieces(connection, id);
        savePieces(connection, id, janggi);
    }

    private void updateGameRecord(Connection connection, Long id, Janggi janggi) throws SQLException {
        String sql = "UPDATE game SET turn = ?, ongoing = ?, state_type = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            bindGameUpdateParams(statement, id, janggi);
            statement.executeUpdate();
        }
    }

    private void bindGameUpdateParams(PreparedStatement statement, Long id, Janggi janggi) throws SQLException {
        statement.setString(1, janggi.currentTurn().name());
        statement.setBoolean(2, janggi.isOnGoing());
        statement.setString(3, janggi.getStateType());
        statement.setLong(4, id);
    }

    private void deleteAllPieces(Connection connection, Long id) throws SQLException {
        String sql = "DELETE FROM piece WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Janggi> findById(Long id) {
        String sql = "SELECT * FROM game WHERE id = ?";
        try (Connection connection = DBConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            return executeGameSelect(connection, statement, id);
        } catch (SQLException e) {
            throw new RuntimeException("게임 조회 중 오류 발생", e);
        }
    }

    private Optional<Janggi> executeGameSelect(Connection connection, PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {
            return reconstructJanggi(connection, resultSet, id);
        }
    }

    private Optional<Janggi> reconstructJanggi(Connection connection, ResultSet resultSet, Long gameId) throws SQLException {
        if (resultSet.next()) {
            GameState gameState = extractGameState(resultSet);
            Board board = loadBoard(connection, gameId);
            return Optional.of(Janggi.reconstruct(board, gameState));
        }
        return Optional.empty();
    }

    private GameState extractGameState(ResultSet resultSet) throws SQLException {
        String turn = resultSet.getString("turn");
        boolean ongoing = resultSet.getBoolean("ongoing");
        String stateType = resultSet.getString("state_type");
        return GameStateFactory.create(turn, ongoing, stateType);
    }

    private Board loadBoard(Connection connection, Long gameId) throws SQLException {
        String sql = "SELECT * FROM piece WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return executePieceSelect(statement, gameId);
        }
    }

    private Board executePieceSelect(PreparedStatement statement, Long gameId) throws SQLException {
        statement.setLong(1, gameId);
        try (ResultSet resultSet = statement.executeQuery()) {
            return extractBoard(resultSet);
        }
    }

    private Board extractBoard(ResultSet resultSet) throws SQLException {
        Map<Position, Piece> pieces = new HashMap<>();
        while (resultSet.next()) {
            putSinglePiece(pieces, resultSet);
        }
        return Board.reconstruct(pieces);
    }

    private void putSinglePiece(Map<Position, Piece> pieces, ResultSet resultSet) throws SQLException {
        int row = resultSet.getInt("row_pos");
        int col = resultSet.getInt("col_pos");
        Position position = Position.of(row, col);

        String pieceType = resultSet.getString("piece_type");
        Camp camp = Camp.valueOf(resultSet.getString("camp"));

        Piece piece = PieceFactory.create(pieceType, camp);

        pieces.put(position, piece);
    }

    @Override
    public List<Janggi> findAll() {
        String sql = "SELECT id FROM game";
        try (Connection connection = DBConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<Janggi> games = new ArrayList<>();
            while (resultSet.next()) {
                findById(resultSet.getLong("id")).ifPresent(games::add);
            }
            return Collections.unmodifiableList(games);
        } catch (SQLException e) {
            throw new RuntimeException("목록 조회 중 오류 발생", e);
        }
    }

    @Override
    public void delete(Long id) {

    }
}
