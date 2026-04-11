package janggi.repository;

import javax.sql.DataSource;
import janggi.domain.game.GameRoom;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.camp.CampType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameRoomRepository {

    private final DataSource dataSource;

    public GameRoomRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long save(GameRoom gameRoom) {
        return insert("INSERT INTO game_rooms(current_turn, game_status) VALUES(?, ?)",
                gameRoom.getCurrentTurn().name(),
                gameRoom.getGameStatus().name());
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

    public List<Long> findAllByGameStatus(GameStatus gameStatus) {
        return executeQuery("SELECT game_room_id FROM game_rooms WHERE game_status = ?",
                resultSet -> {
                    List<Long> ids = new ArrayList<>();
                    while (resultSet.next()) {
                        ids.add(resultSet.getLong("game_room_id"));
                    }
                    return ids;
                }, gameStatus.name());
    }

    public GameRoom findById(long gameRoomId) {
        return executeQuery("SELECT * FROM game_rooms WHERE game_room_id = ?",
                rs -> {
                    if (rs.next()) {
                        return new GameRoom(
                                rs.getLong("game_room_id"),
                                CampType.valueOf(rs.getString("current_turn")),
                                GameStatus.valueOf(rs.getString("game_status")),
                                rs.getTimestamp("start_at").toLocalDateTime(),
                                rs.getTimestamp("end_at") != null
                                        ? rs.getTimestamp("end_at").toLocalDateTime()
                                        : null,
                                rs.getTimestamp("last_updated_at").toLocalDateTime()
                        );
                    }
                    throw new RuntimeException("게임방을 찾을 수 없습니다. gameRoomId: " + gameRoomId);
                }, gameRoomId);
    }

    public void update(GameRoom gameRoom) {
        update("UPDATE game_rooms SET current_turn = ?, game_status = ?, start_at = ?,  end_at = ?, last_updated_at = CURRENT_TIMESTAMP WHERE game_room_id = ?",
                gameRoom.getCurrentTurn().name(),
                gameRoom.getGameStatus().name(),
                gameRoom.getStartAt(),
                gameRoom.getEndAt(),
                gameRoom.getGameRoomId()
        );
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
