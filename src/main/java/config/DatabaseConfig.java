package config;

import dao.DatabaseConnectionManager;
import java.sql.SQLException;

public final class DatabaseConfig {

    public static void setUp() {
        setUpPieceTable();
        setUpPlayerTable();
        setUpBoardTable();
    }

    private static void setUpPieceTable() {
        final var query = "CREATE TABLE IF NOT EXISTS piece (\n"
                + "    `row` INT NOT NULL,\n"
                + "    `column` INT NOT NULL,\n"
                + "    team VARCHAR(50) NOT NULL,\n"
                + "    piece_type VARCHAR(50) NOT NULL\n"
                + ");\n";
        try (final var connection = DatabaseConnectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 기물 테이블 생성 중 오류가 발생했습니다.");
        }
    }

    private static void setUpPlayerTable() {
        final var query = "CREATE TABLE IF NOT EXISTS player (\n"
                + "    name VARCHAR(50) NOT NULL,\n"
                + "    team VARCHAR(50) NOT NULL\n"
                + ");";
        try (final var connection = DatabaseConnectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 플레이어 테이블 생성 중 오류가 발생했습니다.");
        }
    }

    private static void setUpBoardTable() {
        final var query = "CREATE TABLE IF NOT EXISTS board (\n"
                + "    current_turn VARCHAR(50) NOT NULL\n"
                + ");";
        try (final var connection = DatabaseConnectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 보드 테이블 생성 중 오류가 발생했습니다.");
        }
    }
}
