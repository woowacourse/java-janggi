package domain.board;

import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public record BoardSnapshot(Map<Position, Piece> pieces) {

    public BoardSnapshot {
        pieces = Map.copyOf(pieces);
    }

    public Piece pieceAt(Position position) {
        return pieces.get(position);
    }
}
