package infrastructure;

import application.persistence.BoardRepository;
import application.persistence.DbConnector;
import domain.Coordinate;
import domain.board.Board;
import domain.piece.Country;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardJdbcRepository implements BoardRepository {

    private final DbConnector dbConnector;

    public BoardJdbcRepository(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void save(Board board) {
        Map<Coordinate, Piece> pieces = board.getBoard();

        String query = "INSERT INTO board (piece_name, x, y, country) VALUES(?, ?, ?, ?)";
        try (Connection connection = dbConnector.getConnection();
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

    @Override
    public Map<Coordinate, Piece> findAll() {
        String query = "SELECT * FROM board";

        try (Connection connection = dbConnector.getConnection();
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

    @Override
    public void deleteAll() {
        String query = "DELETE FROM board";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
