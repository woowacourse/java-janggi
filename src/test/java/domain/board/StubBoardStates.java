package domain.board;

import domain.Position;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class StubBoardStates {
    private final Map<Position, Piece> boardStates = new HashMap<>();

    public void put(Position position, Piece piece) {
        boardStates.put(position, piece);
    }

    public BoardStates create() {
        return new BoardStates(boardStates);
    }
}
