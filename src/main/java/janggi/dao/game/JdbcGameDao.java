package janggi.dao.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcGameDao implements GameDao {

    private static final String GAME_ID = "game_id";
    private static final String CURRENT_TURN = "current_turn";

    @Override
    public Long saveGame(
            Connection connection,
            String currentTurn
    ) {
        String sql = """
                INSERT INTO game(current_turn)
                VALUES (?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, currentTurn);
            preparedStatement.executeUpdate();
            return getGeneratedKey(preparedStatement);
        } catch (SQLException e) {
            throw new IllegalStateException("game 데이터 삽입에 실패했습니다.");
        }
    }

    private long getGeneratedKey(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    @Override
    public List<GameEntity> findAllGames(Connection connection) {
        String sql = """
                SELECT *
                FROM game
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            List<GameEntity> result = new ArrayList<>();

            while (rs.next()) {
                result.add(new GameEntity(
                        rs.getLong(GAME_ID),
                        rs.getString(CURRENT_TURN)
                ));
            }

            return result;

        } catch (SQLException e) {
            throw new IllegalStateException("게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public GameEntity findGameByGameId(
            Connection connection,
            Long gameId
    ) {
        String sql = """
                SELECT *
                FROM game
                WHERE game_id = (?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                throw new IllegalArgumentException("해당 게임이 존재하지 않습니다.");
            }

            return new GameEntity(
                    rs.getLong(GAME_ID),
                    rs.getString(CURRENT_TURN)
            );

        } catch (SQLException e) {
            throw new IllegalStateException("게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void deleteGameByGameId(Connection connection, Long gameId) {
        String sql = """
                DELETE FROM game
                WHERE game_id = (?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow == 0) {
                throw new IllegalArgumentException("해당 게임이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 삭제에 실패했습니다.", e);
        }
    }

    @Override
    public void updateCurrentTurn(Connection connection, Long gameId, String nextTurn) {
        String sql = """
                UPDATE game
                SET current_turn = (?)
                WHERE game_id = (?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nextTurn);
            preparedStatement.setLong(2, gameId);

            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow == 0) {
                throw new IllegalArgumentException("해당 게임이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 정보 수정에 실패했습니다.", e);
        }
    }
}
