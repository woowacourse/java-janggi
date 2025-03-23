package player;

import java.util.ArrayList;
import piece.Piece;
import java.util.List;
import pieceProperty.Position;

public class Pieces {
    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void removePiece(Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(destination))
                .findFirst()
                .ifPresent(pieces::remove);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public boolean isKingDie() {
        return pieces.stream()
                .noneMatch(Piece::isKing);
    }

    public void validateAllyPieceAtStart(Position presentPosition) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 시작 위치에 아군 기물이 존재하지 않습니다."));
    }

    public void validateAllyPieceAtDestination(Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 도착지에 아군 기물이 존재합니다."));
    }

    public void canPieceMoveTo(Position presentPosition, Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .anyMatch(piece -> piece.canMoveTo(destination));
    }
}
