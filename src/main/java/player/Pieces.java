package player;

import java.util.ArrayList;
import piece.Piece;
import java.util.List;
import pieceProperty.Position;
import pieceProperty.Positions;

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
        if (pieces.stream().anyMatch(piece -> piece.isSamePosition(destination))) {
            throw new IllegalArgumentException("[ERROR] 도착지에 아군 기물이 존재합니다.");
        }
    }

    public void canPieceMoveTo(Position presentPosition, Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .anyMatch(piece -> piece.canMoveTo(destination));
    }

    public Positions makeRoute(Position presentPosition, Position destination) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .map(piece -> piece.makeRoute(destination))
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 시작 위치에 아군 기물이 존재하지 않습니다."));
    }

    public int countObstacle(Positions route) {
        return (int) pieces.stream()
                .filter(route::containsPosition)
                .count();
    }

    public void movePiece(Position presentPosition, Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .ifPresent(piece -> piece.updateChessPiecePositionBy(destination));
    }


    public List<Piece> getPieces() {
        return pieces;
    }

    public Boolean isPoAt(Position presentPosition) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .map(Piece::isPo)
                .orElseThrow();
    }

    public Boolean isExistPoInRoute(Positions route) {
        return pieces.stream()
                .anyMatch(piece -> route.containsPosition(piece) && piece.isPo());
    }

}
