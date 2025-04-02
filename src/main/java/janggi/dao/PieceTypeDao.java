package janggi.dao;

import janggi.dto.PieceTypeDto;
import janggi.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.SQLException;

public class PieceTypeDao {

    private final ConnectionManager connectionManager;

    public PieceTypeDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void insertInitialPieceType() {
        final String query = "INSERT INTO piece_type(name) VALUES(?)";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            for (PieceType pieceType : PieceType.values()) {
                preparedStatement.setString(1, pieceType.getTitle());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 삽입에 실패하였습니다.");
        }
    }


    public PieceTypeDto findPieceTypeById(int id) {
        final String query = "SELECT * FROM piece_type WHERE id = ?";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            final var resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("[ERROR] 데이터 조회에 실패하였습니다.");
            }
            return new PieceTypeDto(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 조회에 실패하였습니다.");
        }
    }

    public void deleteAllPieceTypeIfExists() {
        final String query = "DELETE FROM piece_type";
        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 삭제에 실패하였습니다.");
        }
    }
}
