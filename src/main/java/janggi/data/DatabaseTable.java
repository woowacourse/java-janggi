package janggi.data;

import java.sql.Connection;

public final class DatabaseTable {

    private DatabaseTable() {
    }

    public static void create() {
        try (final var connection = DatabaseConnection.createConnection()) {
            createCampTable(connection);
            createPieceSymbolTable(connection);
            createBoardTable(connection);
            createPieceTable(connection);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createCampTable(Connection connection) {
        final String query = """
                CREATE TABLE IF NOT EXISTS camp (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50) NOT NULL UNIQUE
                );
                """;
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createPieceSymbolTable(Connection connection) {
        final String query = """
                CREATE TABLE IF NOT EXISTS piece_symbol (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50) NOT NULL UNIQUE
                );
                """;
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createBoardTable(Connection connection) {
        final String query = """
                CREATE TABLE IF NOT EXISTS board (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    is_end BOOLEAN NOT NULL DEFAULT FALSE,
                    turn_camp_id INT NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (turn_camp_id) REFERENCES camp(id)
                );
                """;
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createPieceTable(Connection connection) {
        final String query = """
                CREATE TABLE IF NOT EXISTS piece (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    camp_id INT NOT NULL,
                    piece_symbol_id INT NOT NULL,
                    board_id INT NOT NULL,
                    x INT NOT NULL,
                    y INT NOT NULL,
                    FOREIGN KEY (camp_id) REFERENCES camp(id),
                    FOREIGN KEY (piece_symbol_id) REFERENCES piece_symbol(id),
                    FOREIGN KEY (board_id) REFERENCES board(id),
                    CONSTRAINT unique_position UNIQUE (board_id, x, y)
                );
                """;
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
