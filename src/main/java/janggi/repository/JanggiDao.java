package janggi.repository;

import janggi.domain.piece.Side;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.King;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.piece.Rook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("piece 정보를 삽입할 수 없습니다." + e.getMessage());
        }
    }

    public void insertTurn(Side turn) {
        String query = "INSERT INTO turn (turn) VALUES(?)";
        try (Connection connection = getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, turn.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("turn 정보를 삽입할 수 없습니다." + e.getMessage());
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
            System.out.println("piece 정보를 읽어 올 수 없습니다." + e.getMessage());
            return false;
        }
    }

    public List<Piece> loadPieces() {
        List<Piece> loadedPieces = new ArrayList<Piece>();

        String query = "SELECT piece_type, x_position, y_position, side FROM piece";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String pieceType = resultSet.getString(1);
                int x = resultSet.getInt(2);
                int y = resultSet.getInt(3);
                String side = resultSet.getString(4);
                loadedPieces.add(createPiece(pieceType, x, y, Side.valueOf(side)));
            }
            return loadedPieces;
        } catch (SQLException e) {
            System.out.println("piece 정보를 읽어 올 수 없습니다." + e.getMessage());
        }
        return loadedPieces;
    }

    public Side loadTurn() {
        String query = "SELECT turn FROM turn";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String turn = resultSet.getString(1);
                return Side.valueOf(turn);
            }
        } catch (SQLException e) {
            System.out.println("turn 정보를 읽어 올 수 없습니다." + e.getMessage());
        }
        return null;
    }

    public void removeDestinationPiece(Position destination) {
        String query = "DELETE FROM piece WHERE x_position = ? AND y_position = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, destination.getX());
            preparedStatement.setInt(2, destination.getY());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("piece를 삭제 할 수 없습니다." + e.getMessage());
        }
    }

    public void updateMovingPiece(Position start, Position destination) {
        String query = "UPDATE piece SET x_position = ?, y_position = ? WHERE x_position = ? AND y_position = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, destination.getX());
            preparedStatement.setInt(2, destination.getY());
            preparedStatement.setInt(3, start.getX());
            preparedStatement.setInt(4, start.getY());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("piece를 수정 할 수 없습니다." + e.getMessage());
        }
    }

    public void updateTurn(Side turn) {
        String query = "UPDATE turn SET turn = ? WHERE turn = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, Side.opposite(turn).name());
            preparedStatement.setString(2, turn.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("turn을 수정 할 수 없습니다." + e.getMessage());
        }
    }

    public void removePieces() {
        String query = "DELETE FROM piece";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("pieces를 삭제 할 수 없습니다." + e.getMessage());
        }
    }

    public void removeTurn() {
        String query = "DELETE FROM turn";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("turn을 삭제 할 수 없습니다." + e.getMessage());
        }
    }

    private Piece createPiece(String pieceType, int x, int y, Side side) {
        return switch (pieceType) {
            case "Cannon" -> new Cannon(side, x, y);
            case "Elephant" -> new Elephant(side, x, y);
            case "Guard" -> new Guard(side, x, y);
            case "King" -> new King(side, x, y);
            case "Knight" -> new Knight(side, x, y);
            case "Rook" -> new Rook(side, x, y);
            default -> new Pawn(side, x, y);
        };
    }
}
