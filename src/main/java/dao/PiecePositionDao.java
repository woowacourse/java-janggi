package dao;

import domain.Team;
import domain.board.BoardPosition;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PiecePositionDao {

    public void deleteAll(final Connection connection) {
        final var query = "DELETE FROM piece_position WHERE TRUE";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAll(
            final Connection connection,
            final Map<BoardPosition, Piece> board
    ) {
        final var query = "INSERT INTO piece_position (position_x, position_y, piece, team) VALUES (?, ?, ?, ?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            for (final Entry<BoardPosition, Piece> entry : board.entrySet()) {

                final BoardPosition position = entry.getKey();
                final Team team = entry.getValue().getTeam();
                final PieceType pieceType = entry.getValue().getPieceType();

                preparedStatement.setInt(1, position.x());
                preparedStatement.setInt(2, position.y());
                preparedStatement.setString(3, pieceType.name());
                preparedStatement.setString(4, team.name());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    public Map<BoardPosition, Piece> findAll(final Connection connection) {
        final var query = "SELECT * FROM piece_position";
        try (final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();
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
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByBoardPosition(
            final Connection connection,
            final BoardPosition boardPosition
    ) {
        final var query = "DELETE FROM piece_position WHERE position_x = ? AND position_y = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardPosition.x());
            preparedStatement.setInt(2, boardPosition.y());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateByBoardPosition(
            final Connection connection,
            final BoardPosition selectPosition,
            final BoardPosition destinationPosition) {
        final var query = "UPDATE piece_position SET position_x = ?, position_y = ? WHERE position_x = ? AND position_y = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, destinationPosition.x());
            preparedStatement.setInt(2, destinationPosition.y());
            preparedStatement.setInt(3, selectPosition.x());
            preparedStatement.setInt(4, selectPosition.y());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
