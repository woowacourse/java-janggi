package janggi.repository;

import janggi.domain.Side;
import janggi.domain.piece.Piece;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JanggiDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            return null;
        }
    }

    public void createPieceTable() {
        String query = "CREATE TABLE IF NOT EXISTS piece(" +
            "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "piece_type VARCHAR(20) NOT NULL," +
            "x_position INT NOT NULL," +
            "y_position INT NOT NULL," +
            "side VARCHAR(10) NOT NULL)";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("piece 테이블을 생성할 수 없습니다." + e.getMessage());
        }
    }

    public void createTurnTable() {
        String query = "CREATE TABLE IF NOT EXISTS turn(" +
            "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            "turn VARCHAR(10) NOT NULL)";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("turn 테이블을 생성할 수 없습니다." + e.getMessage());
        }
    }

    public void insertPiece(Piece piece) {
        String query = "INSERT INTO piece (piece_type, x_position, y_position, side) " +
            "VALUES(?, ?, ?, ?)";
        try (Connection connection = getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, piece.getClass().getSimpleName());
            preparedStatement.setInt(2, piece.getXPosition());
            preparedStatement.setInt(3, piece.getYPosition());
            preparedStatement.setString(4, piece.getSide().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("piece 정보를 삽입할 수 업습니다." + e.getMessage());
        }
    }

    public void insertTurn(Side turn) {
        String query = "INSERT INTO turn (turn) VALUES(?)";
        try (Connection connection = getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, turn.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("turn 정보를 삽입할 수 업습니다." + e.getMessage());
        }
    }

    public boolean hasGamePiece() {
        String query = "SELECT EXISTS (SELECT 1 FROM piece)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1).equals("1");
            }
            return false;
        } catch (SQLException e) {
            System.out.println("piece 정보를 읽어올 수 업습니다." + e.getMessage());
            return false;
        }
    }
}
