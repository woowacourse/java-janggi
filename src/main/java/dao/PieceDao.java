package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.Position;
import piece.PieceType;
import piece.Team;

public class PieceDao {

    private final Connection connection;

    public PieceDao(final Connection connection) {
        this.connection = connection;
    }

    public void saveAll(final List<PieceEntity> pieceEntities) {
        String sql = """
                INSERT INTO piece (row_value, column_value, piece_type, team)
                VALUES (?, ?, ?, ?);
                """;
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (PieceEntity pieceEntity : pieceEntities) {
                preparedStatement.setInt(1, pieceEntity.rowValue());
                preparedStatement.setInt(2, pieceEntity.columnValue());
                preparedStatement.setString(3, pieceEntity.pieceType().name());
                preparedStatement.setString(4, pieceEntity.team().name());

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PieceEntity> findAll() {
        String sql = """
                SELECT row_value, column_value, piece_type, team
                FROM piece
                """;
        try (
                final PreparedStatement preparedStatement = connection.prepareStatement(sql);
                final ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            List<PieceEntity> pieceEntities = new ArrayList<>();
            while (resultSet.next()) {
                pieceEntities.add(new PieceEntity(null, resultSet.getInt(1), resultSet.getInt(2),
                        PieceType.valueOf(resultSet.getString(3)), Team.valueOf(resultSet.getString(4))));
            }
            return pieceEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAndUpdatePosition(final Position originPosition, final Position updatePosition) {
        String deleteSql = """
                DELETE FROM piece
                WHERE row_value = ? AND column_value = ?;
                """;
        String updateSql = """
                UPDATE piece
                SET row_value = ?, column_value = ?
                WHERE row_value = ? AND column_value = ?;
                """;
        try (
                final PreparedStatement deleteStmt = connection.prepareStatement(deleteSql);
                final PreparedStatement updateStmt = connection.prepareStatement(updateSql)
        ) {
            deleteStmt.setInt(1, updatePosition.getRow());
            deleteStmt.setInt(2, updatePosition.getColumn());
            deleteStmt.executeUpdate();

            updateStmt.setInt(1, updatePosition.getRow());
            updateStmt.setInt(2, updatePosition.getColumn());
            updateStmt.setInt(3, originPosition.getRow());
            updateStmt.setInt(4, originPosition.getColumn());
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 업데이트 실패", e);
        }
    }

    public void removeAll() {
        String sql = "DELETE FROM piece;";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
