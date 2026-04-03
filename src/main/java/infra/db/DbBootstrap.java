package infra.db;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.h2.tools.RunScript;

public final class DbBootstrap {
    private static final String SCHEMA_PATH = "/db/schema.sql";
    private static final String[] REQUIRED_TABLES = {"GAME", "BOARD"};

    private DbBootstrap() {
    }

    public static void initialize() {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            if (isSchemaMissing(connection)) {
                runSchema(connection);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("DB 초기화에 실패했습니다.", e);
        }
    }

    private static boolean isSchemaMissing(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        for (String tableName : REQUIRED_TABLES) {
            if (!hasTable(metaData, tableName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasTable(DatabaseMetaData metaData, String tableName) throws SQLException {
        try (ResultSet resultSet = metaData.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return resultSet.next();
        }
    }

    private static void runSchema(Connection connection) {
        try (Reader reader = openSchemaReader()) {
            RunScript.execute(connection, reader);
        } catch (Exception e) {
            throw new IllegalStateException("DB 스키마 적용에 실패했습니다.", e);
        }
    }

    private static Reader openSchemaReader() {
        var stream = DbBootstrap.class.getResourceAsStream(SCHEMA_PATH);
        if (stream == null) {
            throw new IllegalStateException("스키마 파일을 찾을 수 없습니다: " + SCHEMA_PATH);
        }
        return new InputStreamReader(stream, StandardCharsets.UTF_8);
    }
}
