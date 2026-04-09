package repository;

import domain.piece.Team;
import domain.state.JanggiGame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    public static final String GAME_ROOM_DOES_NOT_EXISTS = "존재하지 않는 게임 방 입니다.";

    public long save(Team turn, String title) {
        String sql = "INSERT INTO GAME_ROOM (TURN, TITLE, IS_FINISHED) VALUES (?,?, ?)";
        long id = 0L;

        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            psmt.setString(1, turn.name());
            psmt.setString(2, title);
            psmt.setBoolean(3, false);

            int affectedRows = psmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = psmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public List<GameRoomInfo> getAll() {
        String sql = "SELECT ID, TITLE FROM GAME_ROOM WHERE IS_FINISHED = FALSE";

        List<GameRoomInfo> infos = new ArrayList<>();
        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {

            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                infos.add(new GameRoomInfo(resultSet.getLong(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return infos;
    }

    public Team getCurrentTeam(long gameId) {
        String sql = "SELECT TURN FROM GAME_ROOM WHERE ID = ?";

        Team team = null;
        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setLong(1, gameId);
            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                team = Team.valueOf(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return team;

    }

    public boolean isFinished(long gameId) {
        String sql = "SELECT IS_FINISHED FROM GAME_ROOM WHERE ID = ?";

        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setLong(1, gameId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
                throw new IllegalArgumentException(GAME_ROOM_DOES_NOT_EXISTS);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGame(Connection conn, long gameId, JanggiGame game) {
        String sql = "UPDATE GAME_ROOM SET TURN = ?, IS_FINISHED = ? WHERE id = ?";

        try (
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setString(1, game.getTurn().name());
            psmt.setBoolean(2, game.isFinished());
            psmt.setLong(3, gameId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isExists(long gameId) {
        String sql = "SELECT 1 FROM GAME_ROOM WHERE id = ?";

        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setLong(1, gameId);

            try (ResultSet rs = psmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
