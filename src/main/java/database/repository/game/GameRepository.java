package database.repository.game;

import database.connector.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import object.team.Country;

public class GameRepository implements JdbcGameRepository {

    private final DatabaseConnector connector;

    public GameRepository(DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public void initializeTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS game;";
        String createTableQuery = """
                CREATE TABLE game (
                    id INT PRIMARY KEY,
                    turn_team VARCHAR(10),
                    is_end BOOLEAN DEFAULT FALSE,
                    score_han INT DEFAULT 0,
                    score_cho INT DEFAULT 0
                );
                """;
        String insertInitialQuery = """
                INSERT INTO game (id, turn_team, is_end, score_han, score_cho)
                VALUES (1, 'HAN', FALSE, 0, 0);
                """;

        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(dropTableQuery);
            statement.execute(createTableQuery);
            statement.execute(insertInitialQuery);

        } catch (SQLException e) {
            throw new RuntimeException("데이터 베이스 연결 중 에러가 발생헀습니다." + e);
        }
    }

    @Override
    public GameDto loadGame() {
        String query = "SELECT * FROM game WHERE id = 1";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return new GameDto(
                        Country.valueOf(resultSet.getString("turn_team")),
                        resultSet.getBoolean("is_end"),
                        resultSet.getInt("score_han"),
                        resultSet.getInt("score_cho")
                );
            }

            throw new IllegalStateException("게임 상태를 찾을 수 없습니다.");

        } catch (SQLException e) {
            throw new RuntimeException("데이터 베이스 연결 중 에러가 발생헀습니다." + e);
        }
    }

    @Override
    public void saveGame(Country turnTeam, boolean isEnd, int scoreHan, int scoreCho) {
        String query = "UPDATE game SET turn_team = ?, is_end = ?, score_han = ?, score_cho = ? WHERE id = 1";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, turnTeam.name());
            preparedStatement.setBoolean(2, isEnd);
            preparedStatement.setInt(3, scoreHan);
            preparedStatement.setInt(4, scoreCho);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("데이터 베이스 연결 중 에러가 발생헀습니다." + e);
        }
    }
}
