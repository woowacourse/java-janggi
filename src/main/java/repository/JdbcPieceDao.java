package repository;

import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JdbcPieceDao {

    public void saveAll(
            final Connection connection,
            final Long gameId,
            final Map<Position, Piece> pieces
    ) throws SQLException {

        String sql = "INSERT INTO piece (game_id, piece_type, team, position_column, position_row) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Entry<Position, Piece> entry : pieces.entrySet()) {
                setPieceStatement(preparedStatement, gameId, entry.getKey(), entry.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    public void deleteByGameId(final Connection connection, final Long gameId) throws SQLException {
        String sql = "DELETE FROM piece WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.executeUpdate();
        }
    }

    public Map<Position, Piece> findByGameId(final Connection connection, final Long gameId) throws SQLException {
        String sql = "SELECT piece_type, team, position_column, position_row FROM piece WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return mapToPieces(resultSet);
            }
        }
    }

    private void setPieceStatement(
            final PreparedStatement preparedStatement,
            final Long gameId,
            final Position position,
            final Piece piece
    ) throws SQLException {
        preparedStatement.setLong(1, gameId);
        preparedStatement.setString(2, piece.getPieceType().toString());
        preparedStatement.setString(3, piece.getTeam().toString());
        preparedStatement.setInt(4, position.column());
        preparedStatement.setInt(5, position.row());
    }

    private Map<Position, Piece> mapToPieces(final ResultSet resultSet) throws SQLException {
        Map<Position, Piece> pieces = new HashMap<>();

        while (resultSet.next()) {
            Position position = Position.of(
                    resultSet.getInt("position_column"),
                    resultSet.getInt("position_row")
            );
            Team team = Team.valueOf(resultSet.getString("team"));
            PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));

            pieces.put(position, new Piece(team, pieceType));
        }
        return pieces;
    }
}
