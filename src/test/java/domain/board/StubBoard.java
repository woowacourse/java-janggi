package domain.board;

import domain.place.Place;
import domain.place.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class StubBoard {
    private final Map<Position, Place> board;

    public StubBoard() {
        this.board = BoardFactory.setUpEmpty();
    }

    public StubBoard put(Position position, Piece piece) {
        board.put(position, piece);

        return this;
    }

    public Board create() {
        return new Board(new HashMap<>(board));
    }

}
