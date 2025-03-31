package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.Position;
import piece.PieceType;
import piece.Team;

public class PieceDao {

    private static final String IP = "localhost";
    private static final String PORT = "13306";
    private static final String DATABASE_NAME = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void saveAll(final List<PieceEntity> pieceEntities) {
        String sql = """
                INSERT INTO piece (row_value, column_value, piece_type, team)
                VALUES (?, ?, ?, ?);
                """;
        try (
                final Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
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
                final Connection connection = getConnection();
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

    public void removePieceByPosition(final Position position) {
        String sql = """
                DELETE FROM piece
                WHERE row_value = ? AND column_value = ?;
                """;
        try (
                final Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, position.getRow());
            preparedStatement.setInt(2, position.getColumn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void updatePieceByOriginPosition(final Position originPosition, final Position updatePosition) {
        String sql = """
                UPDATE piece
                SET row_value = ?, column_value = ?
                WHERE row_value = ? AND column_value = ?;
                """;
        try (
                final Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, updatePosition.getRow());
            preparedStatement.setInt(2, updatePosition.getColumn());
            preparedStatement.setInt(3, originPosition.getRow());
            preparedStatement.setInt(4, originPosition.getColumn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAll() {
        String sql = "DELETE FROM piece;";
        try (
                final Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE_NAME + OPTION, USERNAME, PASSWORD
            );
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.");
        }
    }

}
