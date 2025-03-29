package infrastructure;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class BoardRepository {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Board board) {
        Map<Coordinate, Piece> pieces = board.getBoard();

        String query = "INSERT INTO board (piece_name, x, y) VALUES(?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Map.Entry<Coordinate, Piece> entry : pieces.entrySet()) {
                Coordinate coordinate = entry.getKey();
                Piece piece = entry.getValue();

                preparedStatement.setString(1, piece.getType().getPieceName());
                preparedStatement.setInt(2, coordinate.row());
                preparedStatement.setInt(3, coordinate.col());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
