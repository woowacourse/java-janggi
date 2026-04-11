package team.janggi.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import team.janggi.application.JdbcExecutor;
import team.janggi.domain.Game;
import team.janggi.domain.Team;

public class GameRoomRepository {
    private final JdbcExecutor jdbcExecutor;

    public GameRoomRepository(JdbcExecutor jdbcExecutor) {
        this.jdbcExecutor = jdbcExecutor;
    }

    public Game findById(long gameRoomId) {
        final String sql = """
                   SELECT 
                       game_room_id,
                       current_turn,
                       created_dt
                   FROM GAME_ROOM
                   WHERE game_room_id = ?
                """;
        return jdbcExecutor.execute(sql, statement -> {
                Game game = null;
                try {
                    statement.setLong(1, gameRoomId);
                    final ResultSet resultSet = statement.executeQuery();

                    int rowCount = 0;
                    while (resultSet.next()) {
                        if (rowCount++ > 0) {
                            throw new IllegalStateException("1개 이상의 게임룸이 존재합니다.");
                        }
                        final long id = resultSet.getLong("game_room_id");
                        final String currentTurn = resultSet.getString("current_turn");

                        game = new Game(id, Team.valueOf(currentTurn));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("게임룸 조회에 실패하였습니다.", e);
                }

                return game;
            });
    }



    public long save(Game game) {
        final String sql = """
                MERGE INTO game_room g
                USING (VALUES (?, ?, ?)) t(egame_room_id, current_turn, created_dt)
                    ON g.game_room_id = t.egame_room_id
                WHEN MATCHED THEN
                    UPDATE SET current_turn = t.current_turn
                WHEN NOT MATCHED THEN
                    INSERT (current_turn, created_dt)
                    VALUES (t.current_turn, t.created_dt);
                """;
        return jdbcExecutor.execute(sql, Statement.RETURN_GENERATED_KEYS, statement -> {
                    try {
                        setLongParameter(statement, 1, game.getId());
                        statement.setString(2, game.getCurrentTurn().name());
                        statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

                        statement.executeUpdate();

                        final ResultSet generatedKeys = statement.getGeneratedKeys();
                        generatedKeys.next();

                        return generatedKeys.getLong(1);
                    } catch (SQLException e) {
                        throw new RuntimeException("게임룸 저장에 실패하였습니다.", e);
                    }
                });
    }

    private void setLongParameter(PreparedStatement statement, int parameterIndex, Long value) throws SQLException {
        if (value != null) {
            statement.setLong(parameterIndex, value);
            return;
        }

        statement.setNull(parameterIndex, Types.BIGINT);
    }
}
