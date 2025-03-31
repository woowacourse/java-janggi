package database.repository.piece;

import database.connector.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import object.coordinate.Coordinate;
import object.piece.Piece;
import object.piece.PieceType;
import object.team.Country;

public class PieceRepository implements JdbcPieceRepository {

    private final DatabaseConnector connector;

    public PieceRepository(DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public void initializeTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS piece;";
        String createTableQuery = """
                CREATE TABLE piece (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    x INT NOT NULL,
                    y INT NOT NULL,
                    type VARCHAR(10) NOT NULL,
                    team VARCHAR(10) NOT NULL
                );
                """;

        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(dropTableQuery);
            statement.execute(createTableQuery);

        } catch (SQLException e) {
            throw new RuntimeException("데이터 베이스 연결 중 에러가 발생헀습니다." + e);
        }
    }

    @Override
    public PieceDto loadPieces() {
        String query = "SELECT x, y, type, team FROM piece";
        Map<Coordinate, Piece> hanPieces = new HashMap<>();
        Map<Coordinate, Piece> choPieces = new HashMap<>();

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                PieceType type = PieceType.valueOf(resultSet.getString("type"));
                Country country = Country.valueOf(resultSet.getString("team"));

                Coordinate coordinate = new Coordinate(x, y);
                Piece piece = new Piece(country, type);

                if (country.isSameCountry(Country.CHO)) {
                    choPieces.put(coordinate, piece);
                    continue;
                }
                if (country.isSameCountry(Country.HAN)) {
                    hanPieces.put(coordinate, piece);
                    continue;
                }
                throw new IllegalStateException("존재하지 않는 팀입니다.");
            }
            return new PieceDto(hanPieces, choPieces);

        } catch (SQLException e) {
            throw new RuntimeException("데이터 베이스 연결 중 에러가 발생헀습니다." + e);
        }
    }

    @Override
    public void savePieces(Map<Coordinate, Piece> pieces) {
        String truncateQuery = "TRUNCATE TABLE piece";
        String insertQuery = "INSERT INTO piece (x, y, type, team) VALUES (?, ?, ?, ?)";

        try (final Connection connection = connector.getConnection();
             final Statement truncateStatement = connection.createStatement();
             final PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            truncateStatement.execute(truncateQuery);

            for (Entry<Coordinate, Piece> entry : pieces.entrySet()) {
                Coordinate coordinate = entry.getKey();
                Piece piece = entry.getValue();

                insertStatement.setInt(1, coordinate.getX());
                insertStatement.setInt(2, coordinate.getY());
                insertStatement.setString(3, piece.getPieceType().name());
                insertStatement.setString(4, piece.getCountry().name());
                insertStatement.addBatch();
            }

            insertStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("데이터 베이스 연결 중 에러가 발생헀습니다." + e);
        }
    }
}
