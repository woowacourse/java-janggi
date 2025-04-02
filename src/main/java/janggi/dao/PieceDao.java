package janggi.dao;

import janggi.dto.PieceDto;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PieceDao {

    private final ConnectionManager connectionManager;

    public PieceDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void insertPieces(Map<Position, Piece> pieces) {
        final String query = """
                    INSERT INTO piece (team_id, piece_type_id, x, y)
                    VALUES (
                        (SELECT id FROM team WHERE name = ?),
                        (SELECT id FROM piece_type WHERE name = ?),
                        ?, ?
                    )
                """;

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                preparedStatement.setString(1, piece.getTeamType().getTitle());
                preparedStatement.setString(2, piece.getPieceType().getTitle());
                preparedStatement.setInt(3, position.getX());
                preparedStatement.setInt(4, position.getY());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 삽입에 실패하였습니다.");
        }
    }

    public List<PieceDto> findPieces() {
        final String query = "SELECT * FROM piece";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            List<PieceDto> pieces = new ArrayList<>();

            while (resultSet.next()) {
                pieces.add(new PieceDto(
                        resultSet.getInt("id"),
                        resultSet.getInt("team_id"),
                        resultSet.getInt("piece_type_id"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")
                ));
            }

            return pieces;
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 조회에 실패하였습니다.");
        }
    }

    public void deleteAllPieceIfExists() {
        final String query = "DELETE FROM piece";
        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 삭제에 실패하였습니다.");
        }
    }
}
