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
import java.util.ArrayList;
import java.util.List;

public class PieceDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void addPiece(Piece piece, Point point) {
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

    public void updatePieceByPoint(Point point, Piece piece) {
        final var query = "UPDATE piece SET type = ?, camp = ? WHERE pos_x = ? AND pos_y = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            Camp camp = piece.getCamp();
            preparedStatement.setString(1, piece.getPieceType().getName(camp));
            preparedStatement.setString(2, camp.getName());
            preparedStatement.setInt(3, point.getX());
            preparedStatement.setInt(4, point.getY());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePieceByPoint(Point point) {
        final var query = "DELETE FROM piece WHERE pos_x = ? AND pos_y = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, point.getX());
            preparedStatement.setInt(2, point.getY());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Camp findWinningCamp() {
        String query = "SELECT * FROM piece WHERE type = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, PieceType.GENERAL.getName(null));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Camp.from(resultSet.getString("camp"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int getGeneralCount() {
        String query = "SELECT * FROM piece WHERE type = ?";
        int generalCount = 0;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, PieceType.GENERAL.getName(null));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                generalCount += 1;
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return generalCount;
    }

    public List<Piece> findAllCampPieces(Camp camp) {
        String query = "SELECT * FROM piece WHERE camp = ?";
        List<Piece> hanPieces = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String type = resultSet.getString("type");
                hanPieces.add(PieceType.toPiece(type, camp));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return hanPieces;
    }

    public void resetPiece() {
        String query = "TRUNCATE TABLE piece";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeQuery();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPieceEmpty() {
        String query = "SELECT EXISTS (SELECT 1 FROM piece)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
            return true;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
