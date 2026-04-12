package janggi.db;

import janggi.domain.GameContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDao {

    private static final int FIX_GAME_ID = 1;

    private GameDao() {
    }

    public static void deleteGameTable(Connection connection) {
        final String sql = "DELETE FROM GAME WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, FIX_GAME_ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    public static void insertCurrentTurn(Connection connection, GameContext gameContext) {
        final String sql = "INSERT INTO game (id, current_turn) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, FIX_GAME_ID);
            statement.setString(2, gameContext.currentTeamType().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    public static String selectCurrentTurn(Connection connection) {
        final String sql = "SELECT current_turn FROM game WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, FIX_GAME_ID);
            return extractCurrentTurn(statement);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    private static String extractCurrentTurn(PreparedStatement statement) {
        try (ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getString("current_turn");
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    public static boolean hasGameData(Connection connection) {
        final String sql = "SELECT COUNT(*) FROM game WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, FIX_GAME_ID);
            return isGamePresent(statement);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    private static boolean isGamePresent(PreparedStatement statement) {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("COUNT(*)") > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }
}
