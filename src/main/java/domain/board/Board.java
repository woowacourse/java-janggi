package domain.board;

import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
        fillEmptyPositions();
    }

    private void fillEmptyPositions() {
        Position.allPositions()
                .forEach(pos -> pieces.putIfAbsent(pos, EmptyPiece.getInstance()));
    }

    public void move(Position source, Position destination) {
        Piece piece = pieceAt(source);
        piece.validateMove(source, destination, this::pieceAt);
        applyMove(source, destination, piece);
    }

    private void applyMove(Position source, Position destination, Piece piece) {
        pieces.put(destination, piece);
        pieces.put(source, EmptyPiece.getInstance());
    }

    public Piece pieceAt(Position position) {
        return pieces.get(position);
    }
}
