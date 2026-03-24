package domain;

import domain.vo.Position;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    private Board() {
        board = new HashMap<>();
        board.put(Position.of(0, 0), new Piece());
        board.put(Position.of(0, 1), new Piece());
        board.put(Position.of(0, 2), new Piece());
        board.put(Position.of(0, 3), new Piece());
        board.put(Position.of(0, 4), new Piece());
        board.put(Position.of(0, 6), new Piece());
        board.put(Position.of(0, 7), new Piece());
        board.put(Position.of(0, 8), new Piece());
        board.put(Position.of(1, 4), new Piece());
        board.put(Position.of(2, 1), new Piece());
        board.put(Position.of(2, 8), new Piece());
        board.put(Position.of(3, 0), new Piece());
        board.put(Position.of(3, 2), new Piece());
        board.put(Position.of(3, 4), new Piece());
        board.put(Position.of(3, 6), new Piece());
        board.put(Position.of(3, 8), new Piece());

        board.put(Position.of(9, 0), new Piece());
        board.put(Position.of(9, 1), new Piece());
        board.put(Position.of(9, 2), new Piece());
        board.put(Position.of(9, 3), new Piece());
        board.put(Position.of(9, 4), new Piece());
        board.put(Position.of(9, 6), new Piece());
        board.put(Position.of(9, 7), new Piece());
        board.put(Position.of(9, 8), new Piece());
        board.put(Position.of(8, 4), new Piece());
        board.put(Position.of(7, 1), new Piece());
        board.put(Position.of(7, 8), new Piece());
        board.put(Position.of(6, 0), new Piece());
        board.put(Position.of(6, 2), new Piece());
        board.put(Position.of(6, 4), new Piece());
        board.put(Position.of(6, 6), new Piece());
        board.put(Position.of(6, 8), new Piece());
    }

    public static Board of() {
        return new Board();
    }
}
