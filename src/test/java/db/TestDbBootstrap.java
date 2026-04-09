package db;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import org.h2.tools.RunScript;

public final class TestDbBootstrap {
    private static final String SCHEMA_PATH = "/db/schema.sql";

    private TestDbBootstrap() {
    }

    public static void initializeTestDb() {
        ConfigLoader.setConfigFile("application-test.properties");
        try (Connection connection = DbConnectionFactory.createConnection()) {
            runSchema(connection);
        } catch (SQLException e) {
            throw new IllegalStateException("테스트 DB 초기화에 실패했습니다.", e);
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
        return new InputStreamReader(
                Objects.requireNonNull(TestDbBootstrap.class.getResourceAsStream(SCHEMA_PATH)),
                StandardCharsets.UTF_8
        );
    }
}

