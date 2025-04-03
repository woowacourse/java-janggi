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
    public static final String ROW_INDEX = "rowIndex";
    public static final String COLUMN_INDEX = "columnIndex";
    public static final String TEAM_COLOR = "teamColor";
    public static final String PIECE_TYPE = "pieceType";
    private final DBConnection dbConnection;

    public BoardDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void updateOccupiedPositions(Board board) {
        OccupiedPositions occupiedPositions = board.generateOccupiedPositions();
        dbConnection.executeUpdate("DELETE FROM board");
        for (Entry<Position, PieceIdentity> entry : occupiedPositions.getPositions().entrySet()) {
            dbConnection.executeUpdate("INSERT INTO board VALUES (?, ?, ?, ?)",
                    String.valueOf(entry.getKey().row()),
                    String.valueOf(entry.getKey().column()),
                    String.valueOf(entry.getValue().color()),
                    String.valueOf(entry.getValue().getPieceType())
            );
        }
    }

    public Board findBoard() {
        Board board = new Board();
        try (PreparedStatement statement = dbConnection.generatePreparedStatement("SELECT * FROM board");
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Position position = new Position(
                        resultSet.getInt(ROW_INDEX),
                        resultSet.getInt(COLUMN_INDEX)
                );
                Color color = Color.from(resultSet.getString(TEAM_COLOR));
                PieceType pieceType = PieceType.from(resultSet.getString(PIECE_TYPE));
                board.putPiece(position, convertPiece(pieceType, color));
            }
            return board;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece convertPiece(PieceType pieceType, Color color) {
        return switch (pieceType) {
            case CANNON -> new Cannon(color);
            case CHARIOT -> new Chariot(color);
            case ELEPHANT -> new Elephant(color);
            case HORSE -> new Horse(color);
            case KING -> new King(color);
            case GUARD -> new Guard(color);
            case SOLDIER -> new Soldier(color);
        };
    }

}
