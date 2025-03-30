package janggi.dao;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Tank;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceDao {

    public void createTableIfAbsent(final Connection connection) {
        final var query = """
                CREATE TABLE IF NOT EXISTS piece (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    type VARCHAR(64) NOT NULL,
                    side VARCHAR(62) NOT NULL,
                    x INT NOT NULL,
                    y INT NOT NULL
                );""";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(final Board board, Connection connection) {
        final var query = "INSERT INTO piece(type, side, x, y) VALUES(?, ?, ?, ?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            Map<Position, Piece> positionPieces = board.getBoard();
            for (Position position : positionPieces.keySet()) {
                Piece piece = positionPieces.get(position);
                String type = piece.getClass().getSimpleName();
                preparedStatement.setString(1, type);
                preparedStatement.setString(2, piece.getSide().toString());
                preparedStatement.setInt(3, position.x());
                preparedStatement.setInt(4, position.y());
                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsPieces(final Connection connection) {
        final var query = "SELECT * FROM piece";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Piece findByPosition(final Position position, final Connection connection) {
        final var query = "SELECT * FROM piece WHERE x = ? AND y = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createPieceByType(resultSet);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Map<Position, Piece> findAll(final Connection connection) {
        final var query = "SELECT * FROM piece";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            Map<Position, Piece> board = new HashMap<>();
            while (resultSet.next()) {
                Position position = new Position(
                        resultSet.getInt("x"),
                        resultSet.getInt("y")
                );

                Piece piece = createPieceByType(resultSet);
                board.put(position, piece);
            }
            return board;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateByPosition(final Piece piece, final Position start, final Position end,
                                 final Connection connection) {
        final var query = "UPDATE piece SET x = ?, y = ? WHERE type = ? AND x = ? AND y = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, end.x());
            preparedStatement.setInt(2, end.y());
            preparedStatement.setString(3, piece.getClass().getSimpleName());
            preparedStatement.setInt(4, start.x());
            preparedStatement.setInt(5, start.y());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByPosition(final Position position, final Connection connection) {
        final var query = "DELETE FROM piece WHERE x = ? AND y = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear(final Connection connection) {
        final var query = "DELETE FROM piece";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece createPieceByType(final ResultSet resultSet) throws SQLException {
        Piece piece;
        Side side = Side.valueOf(resultSet.getString("side"));
        switch (resultSet.getString("type")) {
            case "Cannon" -> piece = new Cannon(side);
            case "Elephant" -> piece = new Elephant(side);
            case "Guard" -> piece = new Guard(side);
            case "Horse" -> piece = new Horse(side);
            case "King" -> piece = new King(side);
            case "Soldier" -> piece = new Soldier(side);
            case "Tank" -> piece = new Tank(side);
            default -> throw new IllegalArgumentException("유효하지 않은 타입입니다.");
        }
        return piece;
    }
}

