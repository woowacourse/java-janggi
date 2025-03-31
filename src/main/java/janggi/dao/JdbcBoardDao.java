package janggi.dao;

import janggi.domain.Position;
import janggi.entity.BoardEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcBoardDao extends AbstractJdbcDao implements BoardDao {
    @Override
    public void save(final BoardEntity boardEntity, final Position departure) {
        Optional<BoardEntity> boardEntityOptional = findByJanggiIdAndRowAndColumn(boardEntity.janggiId(),
                departure.getRow(),
                departure.getColumn());
        if (boardEntityOptional.isEmpty()) {
            insert(boardEntity);
            return;
        }
        update(new BoardEntity(boardEntityOptional.get().boardId(),
                        boardEntity.janggiId(),
                        boardEntity.pieceType(),
                        boardEntity.team(),
                        boardEntity.row(),
                        boardEntity.column(),
                        boardEntity.isAlive()),
                departure);
    }

    private void insert(final BoardEntity boardEntity) {
        final String query = """
                INSERT INTO board(janggi_id, piece_type, row_num, column_num, team, is_alive)
                VALUES (?, ?, ?, ?, ?, ?)""";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, boardEntity.janggiId());
            preparedStatement.setString(2, boardEntity.pieceType());
            preparedStatement.setInt(3, boardEntity.row());
            preparedStatement.setInt(4, boardEntity.column());
            preparedStatement.setString(5, boardEntity.team());
            preparedStatement.setBoolean(6, boardEntity.isAlive());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(final BoardEntity boardEntity, final Position before) {
        final String query = """
                UPDATE board 
                SET row_num = ?, column_num = ?, is_alive = ? 
                WHERE janggi_id = ? AND row_num = ? AND column_num = ?""";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardEntity.row());
            preparedStatement.setInt(2, boardEntity.column());
            preparedStatement.setBoolean(3, boardEntity.isAlive());

            preparedStatement.setLong(4, boardEntity.janggiId());
            preparedStatement.setInt(5, before.getRow());
            preparedStatement.setInt(6, before.getColumn());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BoardEntity> findByJanggiIdAndRowAndColumn(final long janggiId,
                                                               final int row,
                                                               final int column) {
        final String query = """
                SELECT * 
                FROM board
                where janggi_id = ? AND row_num = ? AND column_num = ?
                """;

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, janggiId);
            preparedStatement.setInt(2, row);
            preparedStatement.setInt(3, column);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new BoardEntity(resultSet.getLong("board_id"),
                        resultSet.getLong("janggi_id"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("team"),
                        resultSet.getInt("row_num"),
                        resultSet.getInt("column_num"),
                        resultSet.getBoolean("is_alive")));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BoardEntity> findAllByJanggiIdAndIsAlive(final long janggiId, boolean isAlive) {
        final String query = "SELECT * FROM board where janggi_id = ? AND is_alive = ?";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, janggiId);
            preparedStatement.setBoolean(2, isAlive);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<BoardEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new BoardEntity(resultSet.getLong("board_id"),
                        resultSet.getLong("janggi_id"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("team"),
                        resultSet.getInt("row_num"),
                        resultSet.getInt("column_num"),
                        resultSet.getBoolean("is_alive")));
            }
            return result;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
