package db;

import java.sql.Connection;

public class DbConnectionTest {
    public static void main(String[] args) {
        try (Connection conn = DbConnection.getConnection()) {
            System.out.println("연결 성공! " + conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
