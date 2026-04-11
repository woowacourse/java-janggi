package janggi.repository;

import janggi.domain.Janggi;
import janggi.domain.Position;
import janggi.domain.piece.Piece;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcJanggiDao implements JanggiDao {

    @Override
    public Long save(Janggi janggi) {
        String insertGameSql = "INSERT INTO game (turn, ongoing) VALUES (?, ?)";
        try (Connection connection = DBConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(insertGameSql,
                    Statement.RETURN_GENERATED_KEYS);
            return executeGameInsert(connection, statement, janggi);
        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 저장 중 오류 발생");
        }
    }

    private Long executeGameInsert(Connection connection, PreparedStatement statement, Janggi janggi) throws SQLException {
        statement.setString(1, janggi.currentTurn().name());
        statement.setBoolean(2, janggi.isOnGoing());
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

    }

    @Override
    public Optional<Janggi> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Janggi> findAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
