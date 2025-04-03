package janggi.dao;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.dto.PieceDto;
import java.sql.Connection;
import java.sql.ResultSet;
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

    public List<PieceDto> findAllPiece() {
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

    public void deletePieceByPositionIfExists(Position arrivalPosition) {
        final String existsQuery = "SELECT 1 FROM piece WHERE x = ? AND y = ? LIMIT 1";
        final String deleteQuery = "DELETE FROM piece WHERE x = ? AND y = ?";

        try (final Connection connection = connectionManager.getConnection();
             final var existsPreparedStatement = connection.prepareStatement(existsQuery);
             final var deletePreparedStatement = connection.prepareStatement(deleteQuery)) {

            existsPreparedStatement.setInt(1, arrivalPosition.getX());
            existsPreparedStatement.setInt(2, arrivalPosition.getY());
            ResultSet rs = existsPreparedStatement.executeQuery();

            if (rs.next()) {
                deletePreparedStatement.setInt(1, arrivalPosition.getX());
                deletePreparedStatement.setInt(2, arrivalPosition.getY());
                deletePreparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 잡힌 기물 삭제에 실패했습니다.", e);
        }
    }

    public void updatePiece(Position currentPosition, Position arrivalPosition) {
        final String updateQuery = "UPDATE piece SET x = ?, y = ? WHERE x = ? AND y = ?";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, arrivalPosition.getX());
            preparedStatement.setInt(2, arrivalPosition.getY());
            preparedStatement.setInt(3, currentPosition.getX());
            preparedStatement.setInt(4, currentPosition.getY());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 이동 중 오류가 발생하였습니다.", e);
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
