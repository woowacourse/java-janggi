package janggi.board;

import janggi.piece.Piece;
import janggi.position.Position;

import java.util.List;

public class Board {

    private final List<Piece> positionedPieces;

    public Board(List<Piece> positionedPieces) {
        this.positionedPieces = positionedPieces;
    }

    public Piece findByPosition(Position startPosition) {
        return positionedPieces.stream()
                .filter(piece -> piece.matchesPosition(startPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다"));
    }
}
