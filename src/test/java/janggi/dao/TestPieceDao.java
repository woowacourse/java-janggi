package janggi.dao;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestPieceDao {
    private static final String SERVER = "localhost:23306";
    private static final String DATABASE = "janggi_test";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void add(Piece piece, Point point) {
        String query = "INSERT INTO piece (type, camp, pos_x, pos_y) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Camp camp = piece.getCamp();
            preparedStatement.setString(1, piece.getPieceType().getName(camp));
            preparedStatement.setString(2, camp.getName());
            preparedStatement.setInt(3, point.getX());
            preparedStatement.setInt(4, point.getY());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Piece findByPoint(Point point) {
        String query = "SELECT * FROM piece WHERE pos_x = ? AND pos_y = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, point.getX());
            preparedStatement.setInt(2, point.getY());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String type = resultSet.getString("type");
                Camp camp = Camp.from(resultSet.getString("camp"));
                return PieceType.toPiece(type, camp);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
