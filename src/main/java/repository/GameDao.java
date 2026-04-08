package repository;

import domain.piece.Camp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameDao {
    public void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table if not exists game (
                        id bigint auto_increment primary key,
                        current_turn varchar(20) not null,
                        finished boolean not null
                    )
                    """);
        }
    }

    public long upsert(Connection connection, Camp currentTurn, boolean finished) throws SQLException {
        Optional<GameData> foundGame = findInProgressLastGame(connection);

        if (foundGame.isPresent()) {
            GameData gameData = foundGame.get();
            long gameId = gameData.id();
            update(connection, gameId, currentTurn, finished);
            return gameId;
        }

        return insert(connection, currentTurn, finished);
    }

    public Optional<GameData> findInProgressLastGame(Connection connection) throws SQLException {
        String sql = """
                select id, current_turn, finished
                from game
                where finished = false
                order by id desc
                limit 1
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(new GameData(
                    resultSet.getLong("id"),
                    Camp.valueOf(resultSet.getString("current_turn")),
                    resultSet.getBoolean("finished")
            ));
        }
    }

    private void update(Connection connection, long gameId, Camp currentTurn, boolean finished) throws SQLException {
        String sql = """
                update game
                set current_turn = ?, finished = ?
                where id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, currentTurn.name());
            statement.setBoolean(2, finished);
            statement.setLong(3, gameId);
            statement.executeUpdate();
        }
    }

    private long insert(Connection connection, Camp currentTurn, boolean finished) throws SQLException {
        String sql = """
                insert into game(current_turn, finished)
                values (?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, currentTurn.name());
            statement.setBoolean(2, finished);
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }
}
