package config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

public class ConnectionManager {
    private static final String DB_URL = "jdbc:h2:~/janggiGame";
    private static final String USER_NAME= "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection(){
        try{
            Server.createWebServer("-web", "-webPort", "8082").start();
            Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD );
            System.out.println("DB 연결 성공!");
            initializeSchema(conn,"schema.sql");
            initializeSchema(conn,"initializer.sql");
            return conn;
        } catch (SQLException e){
            throw new IllegalStateException("DB connection Error",e);
        }
    }

    public static void closeConnection(Connection conn){
        if (conn != null){
            try{
                conn.close();
                System.out.println("DB 닫기 성공!");
            } catch (SQLException e){
                throw new IllegalStateException("Connection close error", e);
            }
        }
    }

    private static void initializeSchema(Connection conn, String filename) {
        try (
                InputStream is = ConnectionManager.class
                        .getClassLoader()
                        .getResourceAsStream(filename)
        ) {

            if (is == null) {
                throw new IllegalStateException("schema.sql not found");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Statement stmt = conn.createStatement();

            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n"); // ⭐ 중요
            }

            String[] queries = sql.toString().split(";");

            for (String query : queries) {
                String trimmed = query.trim();
                if (!trimmed.isEmpty()) {
                    System.out.println("Executing: " + trimmed); // 디버깅
                    stmt.execute(trimmed);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // ⭐ 반드시 출력
            throw new IllegalStateException("Schema initialization failed", e);
        }
    }
}
