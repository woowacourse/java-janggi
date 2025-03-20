package janggi.domain.board;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;
import janggi.domain.piece.Soldier;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public Board createBoard() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(Row.ONE, Column.ONE), new Chariot(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.TWO), new Horse(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.THREE), new Elephant(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.FOUR), new Guard(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.SIX), new Guard(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.SEVEN), new Elephant(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.EIGHT), new Horse(PieceColor.RED));
        board.put(new Position(Row.ONE, Column.NINE), new Chariot(PieceColor.RED));
        board.put(new Position(Row.TWO, Column.FIVE), new General(PieceColor.RED));
        board.put(new Position(Row.THREE, Column.TWO), new Cannon(PieceColor.RED));
        board.put(new Position(Row.THREE, Column.EIGHT), new Cannon(PieceColor.RED));
        board.put(new Position(Row.FOUR, Column.ONE), new Soldier(PieceColor.RED));
        board.put(new Position(Row.FOUR, Column.THREE), new Soldier(PieceColor.RED));
        board.put(new Position(Row.FOUR, Column.FIVE), new Soldier(PieceColor.RED));
        board.put(new Position(Row.FOUR, Column.SEVEN), new Soldier(PieceColor.RED));
        board.put(new Position(Row.FOUR, Column.NINE), new Soldier(PieceColor.RED));
        board.put(new Position(Row.SEVEN, Column.ONE), new Soldier(PieceColor.BLUE));
        board.put(new Position(Row.SEVEN, Column.THREE), new Soldier(PieceColor.BLUE));
        board.put(new Position(Row.SEVEN, Column.FIVE), new Soldier(PieceColor.BLUE));
        board.put(new Position(Row.SEVEN, Column.SEVEN), new Soldier(PieceColor.BLUE));
        board.put(new Position(Row.SEVEN, Column.NINE), new Soldier(PieceColor.BLUE));
        board.put(new Position(Row.EIGHT, Column.TWO), new Cannon(PieceColor.BLUE));
        board.put(new Position(Row.EIGHT, Column.EIGHT), new Cannon(PieceColor.BLUE));
        board.put(new Position(Row.NINE, Column.FIVE), new General(PieceColor.BLUE));
        board.put(new Position(Row.ZERO, Column.ONE), new Chariot(PieceColor.BLUE));
        board.put(new Position(Row.ZERO, Column.TWO), new Horse(PieceColor.BLUE));
        board.put(new Position(Row.ZERO, Column.THREE), new Elephant(PieceColor.BLUE));
        board.put(new Position(Row.ZERO, Column.FOUR), new Guard(PieceColor.BLUE));
        board.put(new Position(Row.ZERO, Column.SIX), new Guard(PieceColor.BLUE));
        board.put(new Position(Row.ZERO, Column.SEVEN), new Elephant(PieceColor.BLUE));
        board.put(new Position(Row.ZERO, Column.EIGHT), new Horse(PieceColor.BLUE));
        board.put(new Position(Row.ZERO, Column.NINE), new Chariot(PieceColor.BLUE));

        return new Board(board);
    }
}
