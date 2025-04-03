package janggi.database;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class JanggiDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void deleteByPosition(final Position position) {
        final String query = """
                DELETE 
                FROM piece 
                WHERE x = ? AND y = ?
                """;

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePiece(final Position position, final Piece piece) {
        final String query = """
                INSERT INTO piece (x, y, piece_type, color) 
                VALUES (?, ?, ?, ?)
                """;

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setString(3, piece.getClass()
                    .getSimpleName()
                    .toUpperCase());
            preparedStatement.setString(4, piece.getColorMessage());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAllPiece(final Map<Position, Piece> pieces) {
        final String query = """
                INSERT INTO piece (x, y, piece_type, color)
                VALUES (?, ?, ?, ?)
                """;

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            for (final var entry : pieces.entrySet()) {
                preparedStatement.setInt(1, entry.getKey()
                        .x());
                preparedStatement.setInt(2, entry.getKey()
                        .y());
                preparedStatement.setString(3, entry.getValue()
                        .getClass()
                        .getSimpleName()
                        .toUpperCase());
                preparedStatement.setString(4, entry.getValue()
                        .getColorMessage());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Pieces findAllPiece() {
        final String query = """
                SELECT * 
                FROM piece
                """;

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query);
             final var resultSet = preparedStatement.executeQuery()) {
            return PieceConverter.convertToPieces(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllPiece() {
        final String query = """
                DELETE 
                FROM piece
                """;

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
