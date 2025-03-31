package dao;

import domain.board.BoardPosition;
import domain.janggi.Team;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PiecePositionDao {

    public void createAllByJanggiId(
            final Connection connection,
            final int janggiId,
            final Map<BoardPosition, Piece> board
    ) throws SQLException {
        final String query = "INSERT INTO piece_position (janggi_id, position_x, position_y, piece, team) VALUES (?, ?, ?, ?, ?)";
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (final Entry<BoardPosition, Piece> entry : board.entrySet()) {

            final BoardPosition position = entry.getKey();
            final Team team = entry.getValue().getTeam();
            final PieceType pieceType = entry.getValue().getPieceType();

            preparedStatement.setInt(1, janggiId);
            preparedStatement.setInt(2, position.x());
            preparedStatement.setInt(3, position.y());
            preparedStatement.setString(4, pieceType.name());
            preparedStatement.setString(5, team.name());

            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
    }


    public Map<BoardPosition, Piece> findAllByJanggiId(
            final Connection connection,
            final int janggiId
    ) throws SQLException {
        final String query = "SELECT * FROM piece_position WHERE janggi_id = ?";
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, janggiId);
        final ResultSet resultSet = preparedStatement.executeQuery();
        final Map<BoardPosition, Piece> piecePositions = new HashMap<>();

        while (resultSet.next()) {
            final BoardPosition boardPosition = new BoardPosition(
                    resultSet.getInt("position_x"),
                    resultSet.getInt("position_y")
            );

            final PieceType pieceType = PieceType.from(resultSet.getString("piece"));
            final Team team = Team.from(resultSet.getString("team"));
            final Piece piece = pieceType.generate(team);

            piecePositions.put(boardPosition, piece);
        }
        return piecePositions;
    }

    public void updateByJanggiIdAndPosition(
            final Connection connection,
            final int janggiId,
            final BoardPosition selectPosition,
            final BoardPosition destinationPosition
    ) throws SQLException {
        final String query = "UPDATE piece_position SET position_x = ?, position_y = ? WHERE janggi_id = ? AND position_x = ? AND position_y = ?";
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, destinationPosition.x());
        preparedStatement.setInt(2, destinationPosition.y());
        preparedStatement.setInt(3, janggiId);
        preparedStatement.setInt(4, selectPosition.x());
        preparedStatement.setInt(5, selectPosition.y());
        preparedStatement.executeUpdate();
    }

    public void deleteByJanggiIdAndPosition(
            final Connection connection,
            final int janggiId,
            final BoardPosition boardPosition
    ) throws SQLException {
        final String query = "DELETE FROM piece_position WHERE janggi_id = ? AND position_x = ? AND position_y = ?";
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, janggiId);
        preparedStatement.setInt(2, boardPosition.x());
        preparedStatement.setInt(3, boardPosition.y());
        preparedStatement.executeUpdate();
    }

    public void deleteAll(final Connection connection) throws SQLException {
        final String query = "DELETE FROM piece_position WHERE TRUE";
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
