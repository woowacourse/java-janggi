package domain.board;

import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(Position source, Position destination) {
        Piece piece = pieceAt(source);
        validateCanMove(piece, source, destination);
        List<Position> route = piece.searchRoute(source, destination);
        List<Piece> piecesOnRoute = route.stream()
                .map(this::pieceAt)
                .collect(Collectors.toList());
        piece.validateRoute(piecesOnRoute, pieceAt(destination));
        applyMove(source, destination, piece);
    }

    private void validateCanMove(Piece piece, Position source, Position destination) {
        if (!piece.canMove(source, destination)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private void applyMove(Position source, Position destination, Piece piece) {
        pieces.put(destination, piece);
        pieces.put(source, EmptyPiece.getInstance());
    }

    public Piece pieceAt(Position position) {
        return pieces.getOrDefault(position, EmptyPiece.getInstance());
    }
}
