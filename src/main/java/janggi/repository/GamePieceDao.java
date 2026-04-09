package janggi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GamePieceDao {

    private static final String GAME_PIECE_ACCESS_FAILED = "[ERROR] 게임 기물 테이블 접근 중 문제가 발생했습니다.";

    public List<StoredGamePiece> findByGameId(Connection connection, long gameId) {
        try (PreparedStatement statement = connection.prepareStatement("""
                select row_position, column_position, piece_type, camp
                from game_piece
                where game_id = ?
                """)) {
            statement.setLong(1, gameId);

            return readBoardSnapshot(statement);
        } catch (SQLException e) {
            throw new DataAccessException(GAME_PIECE_ACCESS_FAILED, e);
        }
    }

    private List<StoredGamePiece> readBoardSnapshot(PreparedStatement statement) throws SQLException {
        List<StoredGamePiece> storedGamePieceData = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                storedGamePieceData.add(toGamePieceRow(resultSet));
            }
        }

        return storedGamePieceData;
    }

    public void save(Connection connection, long gameId, List<StoredGamePiece> storedGamePieces) {
        try {
            delete(connection, gameId);

            if (storedGamePieces.isEmpty()) {
                return;
            }

            insertGamePieceRows(connection, gameId, storedGamePieces);
        } catch (SQLException e) {
            throw new DataAccessException(GAME_PIECE_ACCESS_FAILED, e);
        }
    }

    public void delete(Connection connection, long gameId) {
        try (PreparedStatement statement = connection.prepareStatement("""
                delete from game_piece
                where game_id = ?
                """)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(GAME_PIECE_ACCESS_FAILED, e);
        }
    }

    private void insertGamePieceRows(Connection connection, long gameId, List<StoredGamePiece> storedGamePieces)
            throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                insert into game_piece (game_id, row_position, column_position, piece_type, camp)
                values (?, ?, ?, ?, ?)
                """)) {
            addPieces(statement, gameId, storedGamePieces);
            statement.executeBatch();
        }
    }

    private void addPieces(PreparedStatement statement, long gameId, List<StoredGamePiece> storedGamePieces)
            throws SQLException {
        for (StoredGamePiece storedGamePiece : storedGamePieces) {
            setPieceStatement(statement, gameId, storedGamePiece);
        }
    }

    private StoredGamePiece toGamePieceRow(ResultSet resultSet) throws SQLException {
        return new StoredGamePiece(
                resultSet.getInt("row_position"),
                resultSet.getInt("column_position"),
                resultSet.getString("piece_type"),
                resultSet.getString("camp")
        );
    }

    private void setPieceStatement(PreparedStatement statement, long gameId, StoredGamePiece storedGamePiece)
            throws SQLException {
        statement.setLong(1, gameId);
        statement.setInt(2, storedGamePiece.rowPosition());
        statement.setInt(3, storedGamePiece.columnPosition());
        statement.setString(4, storedGamePiece.pieceType());
        statement.setString(5, storedGamePiece.camp());
        statement.addBatch();
    }
}
