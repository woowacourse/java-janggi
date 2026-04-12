package domain.movement;

import domain.board.BoardState;
import domain.board.Position;
import domain.piece.Piece;
import java.util.Map;

class StubBoardState implements BoardState {

    private final Map<Position, Piece> pieces;

    private StubBoardState(Map<Position, Piece> pieces) {
        this.pieces = Map.copyOf(pieces);
    }

    static BoardState empty() {
        return new StubBoardState(Map.of());
    }

    static BoardState of(Map<Position, Piece> pieces) {
        return new StubBoardState(pieces);
    }

    @Override
    public Piece pieceAt(Position position) {
        Piece piece = pieces.get(position);
        if (piece == null) {
            throw new IllegalArgumentException("[TEST] 해당 위치에 기물이 없습니다: " + position);
        }
        return piece;
    }

    @Override
    public boolean isEmpty(Position position) {
        return !pieces.containsKey(position);
    }
}
