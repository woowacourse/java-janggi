package dao;

import domain.game.JanggiGame;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class JanggiGameDao {

    public void createTable(Connection connection) throws SQLException {
        final var createTableQuery = """
                CREATE TABLE IF NOT EXISTS janggi_game (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    turn VARCHAR(20)
                );
                """;
        try (final var statement = connection.createStatement()) {
            statement.execute(createTableQuery);
        }
    }

    public Long create(Connection connection, Team startingTurn) throws SQLException {
        final var createQuery = """
                INSERT INTO janggi_game (turn) VALUES (?)
                """;
        try (final var preparedStatement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, startingTurn.name());
            preparedStatement.executeUpdate();
            try (final var generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                throw new SQLException("[ERROR] id 생성을 실패하였습니다");
            }
        }
    }

    public void update(JanggiGame janggiGame) {

    }

    public Optional<JanggiGame> find() {
        return Optional.empty();
    }
}
