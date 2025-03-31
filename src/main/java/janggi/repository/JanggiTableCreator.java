package janggi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JanggiTableCreator {

    public static void createPieceTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS piece(" +
            "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "piece_type VARCHAR(20) NOT NULL," +
            "x_position INT NOT NULL," +
            "y_position INT NOT NULL," +
            "side VARCHAR(10) NOT NULL)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("piece 테이블을 생성할 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
    }

    public static void createTurnTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS turn(" +
            "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "turn VARCHAR(10) NOT NULL)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("turn 테이블을 생성할 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
    }
}
