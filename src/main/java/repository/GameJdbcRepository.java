package repository;

import dto.GameRowDetail;
import exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameJdbcRepository implements GameRepository {

    private static final String ID = "id";
    private static final String CURRENT_TURN = "current_turn";

    private static final String CREATE_TABLE_FAIL_MESSAGE = "game 테이블 생성에 실패했습니다.";
    private static final String EXISTS_GAME_FAIL_MESSAGE = "진행 중인 게임 존재 여부 조회에 실패했습니다.";
    private static final String FIND_ONGOING_GAME_FAIL_MESSAGE = "진행 중인 게임 조회에 실패했습니다.";
    private static final String SAVE_FAIL_MESSAGE = "게임 저장에 실패했습니다.";
    private static final String UPDATE_TURN_FAIL_MESSAGE = "턴 업데이트에 실패했습니다.";
    private static final String GAME_END_FAIL_MESSAGE = "게임 종료 처리에 실패했습니다.";

    @Override
    public void createTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS game (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        is_finished BOOLEAN NOT NULL DEFAULT FALSE,
                        current_turn VARCHAR(10) NOT NULL DEFAULT 'CHO'
                    )
                    """);
        } catch (SQLException e) {
            throw new DataAccessException(CREATE_TABLE_FAIL_MESSAGE, e);
        }
    }

    @Override
    public boolean existsGame(Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM game WHERE is_finished = FALSE");
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new DataAccessException(EXISTS_GAME_FAIL_MESSAGE, e);
        }
    }

    public Optional<GameRowDetail> findOngoingGame(Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, current_turn FROM game WHERE is_finished = FALSE ORDER BY id DESC LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }
            return Optional.of(new GameRowDetail(rs.getInt(ID), rs.getString(CURRENT_TURN)));
        } catch (SQLException e) {
            throw new DataAccessException(FIND_ONGOING_GAME_FAIL_MESSAGE, e);
        }
    }

    @Override
    public int save(Connection connection, String turn) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO game (current_turn) VALUES (?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, turn);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataAccessException(SAVE_FAIL_MESSAGE, e);
        }
    }

    @Override
    public void updateTurn(Connection connection, int gameId, String turn) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE game SET current_turn = ? WHERE id = ?");
            stmt.setString(1, turn);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(UPDATE_TURN_FAIL_MESSAGE, e);
        }
    }

    @Override
    public void gameEnd(Connection connection, int gameId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE game SET is_finished = ? WHERE id = ?");
            stmt.setBoolean(1, true);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(GAME_END_FAIL_MESSAGE, e);
        }
    }
}
