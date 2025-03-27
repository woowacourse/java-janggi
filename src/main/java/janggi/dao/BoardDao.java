package janggi.dao;

import janggi.db.DBConnection;
import janggi.model.Board;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import janggi.model.piece.Cannon;
import janggi.model.piece.Chariot;
import janggi.model.piece.Elephant;
import janggi.model.piece.Guard;
import janggi.model.piece.Horse;
import janggi.model.piece.King;
import janggi.model.piece.Piece;
import janggi.model.piece.Soldier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map.Entry;

public class BoardDao {

    public void updateBoard(OccupiedPositions occupiedPositions) {
        DBConnection dbConnection = new DBConnection();
        try (Connection janggiConnection = dbConnection.getJanggiConnection()) {
            janggiConnection.prepareStatement("DELETE FROM board").executeUpdate();
            for (Entry<Position, PieceIdentity> entry : occupiedPositions.getPositions().entrySet()) {
                PreparedStatement statement = janggiConnection.prepareStatement("INSERT INTO board VALUES (?, ?, ?, ?)");
                statement.setString(1, String.valueOf(entry.getKey().row()));
                statement.setString(2, String.valueOf(entry.getKey().column()));
                statement.setString(3, String.valueOf(entry.getValue().getColor()));
                statement.setString(4, String.valueOf(entry.getValue().getPieceType()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Board findBoard() {
        Board board = new Board();
        DBConnection dbConnection = new DBConnection();
        try (Connection janggiConnection = dbConnection.getJanggiConnection()) {
            ResultSet resultSet = janggiConnection.prepareStatement("SELECT * FROM board").executeQuery();
            while(resultSet.next()) {
                Position position = new Position(resultSet.getInt("rowIndex"), resultSet.getInt("columnIndex"));
                Color color = Color.from(resultSet.getString("teamColor"));
                PieceType pieceType = PieceType.from(resultSet.getString("pieceType"));
                board.putPiece(position, convertPiece(pieceType, color));
            }
            return board;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Piece convertPiece(PieceType pieceType, Color color) {
        return switch (pieceType) {
            case CANNON -> new Cannon(color);
            case CHARIOT -> new Chariot(color);
            case ELEPHANT -> new Elephant(color);
            case HORSE -> new Horse(color);
            case KING -> new King(color);
            case GUARD -> new Guard(color);
            case SOLDIER -> new Soldier(color);
            default -> throw new IllegalArgumentException("존재하지 않는 기물 형식입니다." + pieceType);
        };
    }

}
