package dao;

import domain.board.BoardPoint;
import entity.BoardEntity;
import execptions.JanggiArgumentException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    private final JanggiConnection janggiConnection;

    public BoardDao(JanggiConnection janggiConnection) {
        this.janggiConnection = janggiConnection;
    }

    private <T> T executeQuery(String sql, StatementSetter setter, ResultSetExtractor<T> extractor) {
        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(sql)) {

            setter.setValues(preparedStatement);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return extractor.extractData(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeUpdate(String sql, StatementSetter setter) {
        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(sql)) {

            setter.setValues(preparedStatement);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> executeQueryForList(String sql, StatementSetter setter, RowMapper<T> rowMapper) {
        try (var connection = janggiConnection.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            setter.setValues(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(rowMapper.mapRow(resultSet));
                }
                return results;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BoardEntity> getBoardEntities() {
        final var query = "SELECT * FROM board";

        return executeQueryForList(
                query,
                preparedStatement -> {
                },
                resultSet -> new BoardEntity(
                        resultSet.getLong("id"),
                        resultSet.getInt("row_index"),
                        resultSet.getInt("column_index"),
                        resultSet.getLong("piece_id")
                )
        );
    }

    public BoardEntity findByBoardPoint(BoardPoint boardPoint) {

        final var query = "SELECT * FROM board WHERE row_index = ? AND column_index = ?";

        return executeQuery(
                query,
                preparedStatement -> {
                    preparedStatement.setInt(1, boardPoint.row());
                    preparedStatement.setInt(2, boardPoint.column());
                },
                resultSet -> {
                    if (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        int rowIndex = resultSet.getInt("row_index");
                        int columnIndex = resultSet.getInt("column_index");
                        long pieceId = resultSet.getLong("piece_id");

                        return new BoardEntity(id, rowIndex, columnIndex, pieceId);
                    }
                    throw new JanggiArgumentException("조건에 해당하는 보드의 포인트에 기물이 존재하지 않습니다.");
                }
        );
    }

    public void delete(BoardEntity boardEntity) {

        final var query = "DELETE from board WHERE id = ?";

        executeUpdate(
                query,
                preparedStatement -> preparedStatement.setLong(1, boardEntity.getId())
        );

    }

    public void save(BoardPoint boardPoint, long pieceId) {
        final var query = "INSERT INTO board (piece_id, row_index, column_index) "
                + "VALUES (?, ?, ?);";

        executeUpdate(
                query,
                preparedStatement -> {
                    preparedStatement.setLong(1, pieceId);
                    preparedStatement.setLong(2, boardPoint.row());
                    preparedStatement.setLong(3, boardPoint.column());
                }
        );
    }

    public void updatePiece(BoardPoint arrivalBoardPoint, long pieceId) {
        final var query = "UPDATE board SET piece_id = ? WHERE row_index = ? and column_index = ?";

        executeUpdate(
                query,
                preparedStatement -> {
                    preparedStatement.setString(1, String.valueOf(pieceId));
                    preparedStatement.setString(2, String.valueOf(arrivalBoardPoint.row()));
                    preparedStatement.setString(3, String.valueOf(arrivalBoardPoint.column()));
                }
        );
    }
}
