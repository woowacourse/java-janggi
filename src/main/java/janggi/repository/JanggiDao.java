package janggi.repository;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.King;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import janggi.domain.piece.Side;
import janggi.domain.position.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JanggiDao {

    public void insertPiece(Piece piece, Connection connection) {
        String query = "INSERT INTO piece (piece_type, x_position, y_position, side) " +
            "VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, piece.getClass().getSimpleName());
            preparedStatement.setInt(2, piece.getXPosition());
            preparedStatement.setInt(3, piece.getYPosition());
            preparedStatement.setString(4, piece.getSide().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("piece 정보를 삽입할 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
    }

    public void insertTurn(Side turn, Connection connection) {
        String query = "INSERT INTO turn (turn) VALUES(?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, turn.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("turn 정보를 삽입할 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
    }

    public boolean hasGamePiece(Connection connection) {
        String query = "SELECT EXISTS (SELECT 1 FROM piece)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1).equals("1");
            }
            return false;
        } catch (SQLException e) {
            System.out.println("piece 정보를 읽어 올 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            return false;
        }
    }

    public List<Piece> loadPieces(Connection connection) {
        List<Piece> loadedPieces = new ArrayList<Piece>();

        String query = "SELECT piece_type, x_position, y_position, side FROM piece";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String pieceType = resultSet.getString("piece_type");
                int x = resultSet.getInt("x_position");
                int y = resultSet.getInt("y_position");
                String side = resultSet.getString("side");
                loadedPieces.add(createPiece(pieceType, x, y, Side.valueOf(side)));
            }
            return loadedPieces;
        } catch (SQLException e) {
            System.out.println("piece 정보를 읽어 올 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
    }

    public Side loadTurn(Connection connection) {
        String query = "SELECT turn FROM turn";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String turn = resultSet.getString("turn");
                return Side.valueOf(turn);
            }
        } catch (SQLException e) {
            System.out.println("turn 정보를 읽어 올 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
        return null;
    }

    public void removeDestinationPiece(Position destination, Connection connection) {
        String query = "DELETE FROM piece WHERE x_position = ? AND y_position = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, destination.getX());
            preparedStatement.setInt(2, destination.getY());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("piece를 삭제 할 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
    }

    public void updateMovingPiece(Position start, Position destination, Connection connection) {
        String query = "UPDATE piece SET x_position = ?, y_position = ? WHERE x_position = ? AND y_position = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, destination.getX());
            preparedStatement.setInt(2, destination.getY());
            preparedStatement.setInt(3, start.getX());
            preparedStatement.setInt(4, start.getY());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("piece를 수정 할 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(Side turn, Connection connection) {
        String query = "UPDATE turn SET turn = ? WHERE turn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, Side.opposite(turn).name());
            preparedStatement.setString(2, turn.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("turn을 수정 할 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
    }

    public void removePieces(Connection connection) {
        String query = "DELETE FROM piece";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("pieces를 삭제 할 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
        }
    }

    public void removeTurn(Connection connection) {
        String query = "DELETE FROM turn";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("turn을 삭제 할 수 없습니다." + e.getMessage());
            JanggiConnection.rollBack(connection);
            throw new RuntimeException(e);
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
