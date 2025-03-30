package domain.dao;

import domain.piece.Piece;

import java.sql.*;

public class JanggiPieceDao {

    private Connection connection;

    public JanggiPieceDao(Connection connection) {
        this.connection = connection;
    }

    public void createPieceTableIfNotExist() {
        String createPieceTableSQL = """
                CREATE TABLE if not exists piece(
                piece_id INT AUTO_INCREMENT PRIMARY KEY,
                country VARCHAR(5) NOT NULL,
                piece_type VARCHAR(10) NOT NULL,
                game_id INT,
                CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES game(game_id) ON DELETE CASCADE
                );
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(createPieceTableSQL);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] PIECE TABLE 생성 실패");
        }
    }

    public int addPiece(int gameId, Piece piece) {
        String insertPieceSQL = "INSERT INTO piece(country, piece_type, game_id) values(?,?,?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPieceSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, piece.getCountry().getName());
            preparedStatement.setString(2, piece.getPieceType().getName());
            preparedStatement.setInt(3, gameId);
            preparedStatement.executeUpdate();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] PIECE INSERT 실패");
        }
        throw new IllegalStateException("[ERROR] PIECE INSERT 실패");
    }

    public void deletePiecesByGameId(int gameId) {
        String deletePiecesSQL = "DELETE FROM piece WHERE game_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deletePiecesSQL)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] PIECE 삭제 실패");
        }
    }
}
