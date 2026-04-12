package repository;

import domain.piece.Team;
import domain.state.JanggiGame;
import domain.state.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    public static final String GAME_ROOM_DOES_NOT_EXISTS = "존재하지 않는 게임 방 입니다.";

    public long save(Connection conn, GameRoomCreateInfo gameRoomInfo) {
        String sql = "INSERT INTO GAME_ROOM (TURN, TITLE, STATE) VALUES (?, ?, ?)";
        long id = 0L;

        try (
                PreparedStatement psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            psmt.setString(1, gameRoomInfo.turn().name());
            psmt.setString(2, gameRoomInfo.title());
            psmt.setString(3, gameRoomInfo.state().name());

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
        String sql = "SELECT ID, TITLE, STATE, TURN FROM GAME_ROOM WHERE STATE != 'FINISHED'";

        List<GameRoomInfo> infos = new ArrayList<>();
        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {

            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                long gameRoomId = rs.getLong(1);
                String title = rs.getString(2);
                State state = State.valueOf(rs.getString(3));
                Team turn = Team.valueOf(rs.getString(4));
                infos.add(new GameRoomInfo(gameRoomId, title, state, turn));
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
        String sql = "SELECT STATE FROM GAME_ROOM WHERE ID = ?";

        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setLong(1, gameId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1).equals(State.FINISHED.name());
                }
                throw new IllegalArgumentException(GAME_ROOM_DOES_NOT_EXISTS);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGame(Connection conn, long gameId, JanggiGame game) {
        String sql = "UPDATE GAME_ROOM SET TURN = ?, STATE = ? WHERE id = ?";

        try (
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setString(1, game.getTurn().name());
            psmt.setString(2, game.getState().name());
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

    public GameRoomInfo getGameInfo(long gameId) {
        String sql = "SELECT ID, TITLE, STATE, TURN FROM GAME_ROOM WHERE id = ?";
        long gameRoomId = 0L;
        String title = null;
        State state = null;
        Team turn = null;

        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setLong(1, gameId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    gameRoomId = rs.getLong(1);
                    title = rs.getString(2);
                    state = State.valueOf(rs.getString(3));
                    turn = Team.valueOf(rs.getString(4));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new GameRoomInfo(gameRoomId, title, state, turn);
    }
}
