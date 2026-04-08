package db.jdbc;

import db.model.Migration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Deprecated
@SuppressWarnings("unused")
public class SimpleDatabaseMigrator implements DatabaseMigrator {

    private static final Migration BOOTSTRAP_MIGRATION = new Migration(
        0, "create_migration", "/db/migration/V0__create_migration.sql");
    private static final List<Migration> MIGRATIONS = List.of(
        new Migration(1, "init", "/db/migration/V1__init.sql"),
        new Migration(2, "expand_game_and_add_move_history", "/db/migration/V2__expand_game_and_add_move_history.sql")
    );

    private final ConnectionManager connectionManager;

    public SimpleDatabaseMigrator(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void migrate() {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);

            try {
                applyMigration(connection, BOOTSTRAP_MIGRATION);
                for (final Migration migration : MIGRATIONS) {
                    if (isAlreadyApplied(connection, migration.version())) {
                        continue;
                    }
                    applyMigration(connection, migration);
                    insertMigrationHistory(connection, migration);
                }

                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new IllegalStateException("데이터베이스 마이그레이션에 실패했습니다.", e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터베이스 연결에 실패했습니다.", e);
        }
    }

    private boolean isAlreadyApplied(final Connection connection, final int version) throws SQLException {
        final String sql = """
            SELECT 1
            FROM migration
            WHERE version = ?
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, version);

            try (final ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void applyMigration(final Connection connection, final Migration migration) throws SQLException {
        final String migrationSql = readSql(migration.resourcePath());

        for (final String sql : migrationSql.split(";")) {
            final String trimmedSql = sql.trim();
            if (trimmedSql.isEmpty()) {
                continue;
            }

            try (final Statement statement = connection.createStatement()) {
                statement.execute(trimmedSql);
            }
        }
    }

    private void insertMigrationHistory(final Connection connection, final Migration migration) throws SQLException {
        final String sql = """
            INSERT INTO migration(version, description, applied_at)
            VALUES (?, ?, ?)
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, migration.version());
            statement.setString(2, migration.description());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    private String readSql(final String resourcePath) {
        final InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalStateException(resourcePath + " 파일을 찾을 수 없습니다.");
        }

        try (final BufferedReader reader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            final StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            throw new IllegalStateException(resourcePath + " 파일을 읽는 데 실패했습니다.", e);
        }
    }
}