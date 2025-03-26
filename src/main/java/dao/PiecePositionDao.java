package dao;

import static dao.DatabaseConfig.DATABASE;
import static dao.DatabaseConfig.OPTION;
import static dao.DatabaseConfig.PASSWORD;
import static dao.DatabaseConfig.SERVER;
import static dao.DatabaseConfig.USERNAME;

import domain.Team;
import domain.board.BoardPosition;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PiecePositionDao {

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void addAll(final Map<BoardPosition, Piece> board) {
        final var query = "INSERT INTO piece_position (position_x, position_y, piece, team) VALUES (?, ?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            try {
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
            } catch (Exception e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<BoardPosition, Piece> findAll() {
        final var query = "SELECT * FROM piece_position";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

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
}
