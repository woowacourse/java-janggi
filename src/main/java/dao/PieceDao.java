package dao;

import domain.TeamType;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PieceDao {

    public int savePieces(Map<Position, Piece> pieces, Long gameId, Connection connection) {
        int saveCount = 0;
        for (Entry<Position, Piece> positionPiece : pieces.entrySet()) {
            savePiece(positionPiece.getValue(), positionPiece.getKey(), gameId, connection);
            saveCount++;
        }
        return saveCount;
    }

    public Map<Position, Piece> findBoardPiecesByGameId(Long gameId, Connection connection) {
        String query = "SELECT * FROM pieces WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Position, Piece> result = mapPiecesResultSet(resultSet);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public int updatePiecePosition(Long gameId, Position from, Position to, Connection connection) {
        String query = "UPDATE pieces SET position_row = ?, position_column = ? WHERE game_id = ? AND position_row = ? AND position_column = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, to.getRow().getValue());
            preparedStatement.setInt(2, to.getColumn().getValue());
            preparedStatement.setLong(3, gameId);
            preparedStatement.setInt(4, from.getRow().getValue());
            preparedStatement.setInt(5, from.getColumn().getValue());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public int removePiece(Long gameId, Position position, Connection connection) {
        String query = "DELETE FROM pieces WHERE game_id = ? AND position_row = ? AND position_column = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setInt(2, position.getRow().getValue());
            preparedStatement.setInt(3, position.getColumn().getValue());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    private long savePiece(Piece piece, Position position, Long gameId, Connection connection) {
        String query = "INSERT INTO pieces (game_id, position_row, position_column, team_type, piece_type) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setInt(2, position.getRow().getValue());
            preparedStatement.setInt(3, position.getColumn().getValue());
            preparedStatement.setString(4, piece.getTeamType().name());
            preparedStatement.setString(5, piece.getType().name());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    private Map<Position, Piece> mapPiecesResultSet(ResultSet resultSet) {
        Map<Position, Piece> pieces = new HashMap<>();
        try {
            while (resultSet.next()) {
                TeamType teamType = TeamType.valueOf(resultSet.getString("team_type"));
                Piece piece = PieceType.valueOf(resultSet.getString("piece_type")).createPiece(teamType);
                int row = resultSet.getInt("position_row");
                int column = resultSet.getInt("position_column");

                pieces.put(Position.of(row, column), piece);
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }
}
