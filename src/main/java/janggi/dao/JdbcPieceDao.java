package janggi.dao;

import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcPieceDao extends AbstractJdbcDao implements PieceDao {

    @Override
    public void save(final PieceEntity pieceEntity) {
        Optional<PieceEntity> pieceEntityOptional = findByPieceId(pieceEntity.pieceId());
        if (pieceEntityOptional.isEmpty()) {
            insert(pieceEntity);
            return;
        }
        update(pieceEntity);
    }

    @Override
    public Optional<PieceEntity> findByPieceId(long pieceId) {
        final String query = """
                SELECT * 
                FROM piece 
                where piece_id = ?
                """;

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, pieceId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new PieceEntity(resultSet.getLong("piece_id"),
                        resultSet.getLong("board_id"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("team"),
                        resultSet.getInt("row_num"),
                        resultSet.getInt("column_num"),
                        resultSet.getBoolean("is_alive")));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }

    private void insert(final PieceEntity pieceEntity) {
        final String query = """
                INSERT INTO piece(board_id, piece_type, row_num, column_num, team, is_alive)
                VALUES (?, ?, ?, ?, ?, ?)""";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, pieceEntity.boardId());
            preparedStatement.setString(2, pieceEntity.pieceType());
            preparedStatement.setInt(3, pieceEntity.row());
            preparedStatement.setInt(4, pieceEntity.column());
            preparedStatement.setString(5, pieceEntity.team());
            preparedStatement.setBoolean(6, pieceEntity.isAlive());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }

    private void update(final PieceEntity pieceEntity) {
        final String query = """
                UPDATE piece 
                SET row_num = ?, column_num = ?, is_alive = ? 
                WHERE piece_id = ?""";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pieceEntity.row());
            preparedStatement.setInt(2, pieceEntity.column());
            preparedStatement.setBoolean(3, pieceEntity.isAlive());
            preparedStatement.setLong(4, pieceEntity.pieceId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }

    @Override
    public Optional<PieceEntity> findByBoardIdAndRowAndColumn(final long boardId, final int row, final int column) {
        final String query = """
                SELECT * 
                FROM piece
                where board_id = ? AND row_num = ? AND column_num = ?
                """;

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, boardId);
            preparedStatement.setInt(2, row);
            preparedStatement.setInt(3, column);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new PieceEntity(resultSet.getLong("piece_id"),
                        resultSet.getLong("board_id"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("team"),
                        resultSet.getInt("row_num"),
                        resultSet.getInt("column_num"),
                        resultSet.getBoolean("is_alive")));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }

    @Override
    public List<PieceEntity> findAllByBoardIdAndIsAlive(final long boardId, boolean isAlive) {
        final String query = "SELECT * FROM piece where board_id = ? AND is_alive = ?";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, boardId);
            preparedStatement.setBoolean(2, isAlive);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<PieceEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new PieceEntity(resultSet.getLong("piece_id"),
                        resultSet.getLong("board_id"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("team"),
                        resultSet.getInt("row_num"),
                        resultSet.getInt("column_num"),
                        resultSet.getBoolean("is_alive")));
            }
            return result;
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }
}
