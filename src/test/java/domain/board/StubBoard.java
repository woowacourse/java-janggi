package domain.board;

import static domain.board.BoardPolicy.MAX_COLUMN;
import static domain.board.BoardPolicy.MAX_ROW;
import static domain.board.BoardPolicy.MIN_COLUMN;
import static domain.board.BoardPolicy.MIN_ROW;

import domain.place.Empty;
import domain.place.Place;
import domain.place.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class StubBoard {
    private final Map<Position, Place> board;

    public StubBoard() {
        this.board = new HashMap<>();
        setUpEmpty();
    }

    private void setUpEmpty() {
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), new Empty());
            }
        }
    }

    public StubBoard put(Position position, Piece piece) {
        board.put(position, piece);

        return this;
    }

    public Board create() {
        return new Board(new HashMap<>(board));
    }

}
