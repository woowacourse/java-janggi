package janggi.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseManager {

    private static final String DROP_SQL = "drop.sql";
    private static final String SCHEMA_SQL = "schema.sql";

    private static final String URL = "jdbc:h2:./janggi-db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initTable(DdlAuto ddlAuto) {
        String dropSql = readResource(DROP_SQL);
        String schemaSql = readResource(SCHEMA_SQL);

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            if (DdlAuto.CREATE_DROP.equals(ddlAuto)) {
                executeSqlScript(statement, dropSql);
            }
            executeSqlScript(statement, schemaSql);
        } catch (SQLException e) {
            throw new RuntimeException("DB 초기화 실패", e);
        }
    }

    private static String readResource(String fileName) {
        InputStream inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException(fileName + " 파일을 찾을 수 없습니다.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("SQL 파일 읽기 실패", e);
        }
    }

    private static void executeSqlScript(Statement statement, String sqlScript) throws SQLException {
        for (String query : sqlScript.split(";")) {
            if (!query.isBlank()) {
                statement.execute(query.strip());
            }
        }
    }

}
