package janggi.data.dao.mysql;

import janggi.data.DatabaseConnection;
import janggi.data.dao.PieceSymbolDao;
import janggi.data.exception.DatabaseQueryException;
import janggi.piece.PieceSymbol;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MySqlPieceSymbolDao implements PieceSymbolDao {

    @Override
    public void save(PieceSymbol pieceSymbol) {
        final String query = """
                INSERT IGNORE INTO piece_symbol (name)
                VALUES (?)
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceSymbol.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseQueryException("기물 상징 정보를 저장하는 도중 오류가 발생했습니다. %s".formatted(e.getMessage()));
        }
    }

    @Override
    public void saveAll(PieceSymbol... pieceSymbols) {
        for (PieceSymbol pieceSymbol : pieceSymbols) {
            save(pieceSymbol);
        }
    }

    @Override
    public int findIdByName(String name) {
        final String query = """
                SELECT id
                FROM piece_symbol
                WHERE name = ?
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            final var resultSet = preparedStatement.executeQuery();
            return getIdIfExist(resultSet);
        } catch (final SQLException e) {
            throw new DatabaseQueryException("기물 상징 정보를 조회하는 도중 오류가 발생했습니다. %s".formatted(e.getMessage()));
        }
    }

    private int getIdIfExist(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        throw new IllegalArgumentException("해당 이름의 기물 심볼이 존재하지 않습니다.");
    }
}
