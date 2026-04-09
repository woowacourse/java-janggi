package janggi.repository;

import javax.sql.DataSource;
import janggi.domain.game.Game;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.camp.CampType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    private final DataSource dataSource;

    public GameRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long save(Game game) {
        return insert("INSERT INTO games(current_turn, game_status) VALUES(?, ?)", game.getCurrentTurn(), game.getGameStatus());
    }

    private long insert(String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Long> findAllGameIds() {
        return executeQuery("SELECT game_id FROM games",
                resultSet -> {
                    List<Long> ids = new ArrayList<>();
                    while (resultSet.next()) {
                        ids.add(resultSet.getLong("game_id"));
                    }
                    return ids;
                });
    }

    public Game findByGameId(long gameId) {
        return executeQuery("SELECT * FROM games WHERE game_id = ?",
                rs -> {
                    if (rs.next()) {
                        return new Game(
                                rs.getLong("game_id"),
                                CampType.valueOf(rs.getString("current_turn")),
                                GameStatus.valueOf(rs.getString("game_status")),
                                rs.getTimestamp("start_at").toLocalDateTime(),
                                rs.getTimestamp("end_at") != null
                                        ? rs.getTimestamp("end_at").toLocalDateTime()
                                        : null,
                                rs.getTimestamp("last_updated_at").toLocalDateTime(),
                                List.of()
                        );
                    }
                    throw new RuntimeException("게임을 찾을 수 없습니다. gameId: " + gameId);
                },
                gameId
        );
    }

    public void updateTurn(long gameId, CampType currentTurn) {
        update("UPDATE games SET current_turn = ?, last_updated_at = CURRENT_TIMESTAMP WHERE game_id = ?",
                currentTurn.name(), gameId
        );
    }

    public void updateStatus(long gameId, GameStatus gameStatus) {
        update("UPDATE games SET game_status = ?, end_at = CURRENT_TIMESTAMP , last_updated_at = CURRENT_TIMESTAMP WHERE game_id = ?",
                gameStatus.name(), gameId);
    }

    private void update(String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T executeQuery(String sql, ResultSetMapper<T> mapper, Object... params) {
        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            ResultSet rs = preparedStatement.executeQuery();
            return mapper.map(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
