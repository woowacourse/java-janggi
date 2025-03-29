package janggi.data.dao;

import janggi.data.DatabaseConnection;
import janggi.piece.PieceSymbol;
import java.sql.SQLException;

public final class PieceSymbolDao {

    public void saveAll(PieceSymbol... pieceSymbols) {
        for (PieceSymbol pieceSymbol : pieceSymbols) {
            save(pieceSymbol);
        }
    }

    public void save(PieceSymbol pieceSymbol) {
        final String query = """
                INSERT IGNORE INTO piece_symbol (name)
                       VALUES (?)
                """;
        try (final var connection = DatabaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceSymbol.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findIdByName(String name) {
        final String query = "SELECT * FROM piece_symbol WHERE name = ?";
        try (final var connection = DatabaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new IllegalArgumentException("해당 이름의 기물 심볼이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
