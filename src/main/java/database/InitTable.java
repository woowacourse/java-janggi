package database;

import config.DatabaseConfig;
import java.sql.Connection;
import java.sql.Statement;

public class InitTable {
    public static void schemaInit() {
        Connection connection = DatabaseConfig.createConnection();

        try (Statement stmt = connection.createStatement()){
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS game (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    turn varchar(10) NOT NULL,
                    is_finished boolean NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS piece (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    game_id BIGINT NOT NULL ,
                    row_index INT NOT NULL,
                    column_index INT  NOT NULL,
                    piece_type varchar(20)  NOT NULL,
                    team varchar(10) NOT NULL,
                    FOREIGN KEY (game_id) REFERENCES game(id)
                )
            """);

        } catch (Exception e) {
            System.out.println("테이블을 생성하지 못했습니다" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
