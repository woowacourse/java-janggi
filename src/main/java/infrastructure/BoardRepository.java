package infrastructure;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.Country;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

        String query = "INSERT INTO board (piece_name, x, y, country) VALUES(?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Map.Entry<Coordinate, Piece> entry : pieces.entrySet()) {
                Coordinate coordinate = entry.getKey();
                Piece piece = entry.getValue();

                preparedStatement.setString(1, piece.getType().name());
                preparedStatement.setInt(2, coordinate.row());
                preparedStatement.setInt(3, coordinate.col());
                preparedStatement.setString(4, piece.getCountry().name());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Coordinate, Piece> findAll() {
        String query = "SELECT * FROM board";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Map<Coordinate, Piece> pieces = new HashMap<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Coordinate coordinate = new Coordinate(resultSet.getInt("x"), resultSet.getInt("y"));
                Piece piece = Piece.of(
                        Country.valueOf(resultSet.getString("country")),
                        PieceType.valueOf(resultSet.getString("piece_name")));
                pieces.put(coordinate, piece);
            }

            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
