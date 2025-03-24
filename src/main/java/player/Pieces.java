package player;

import java.util.ArrayList;
import piece.Piece;
import java.util.List;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Pieces {
    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void removePiece(final Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(destination))
                .findFirst()
                .ifPresent(pieces::remove);
    }

    public boolean isKingDie() {
        return pieces.stream()
                .noneMatch(Piece::isKing);
    }

    public void validateAllyPieceAtStart(final Position presentPosition) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.formatMessage("시작 위치에 아군 기물이 존재하지 않습니다.")));
    }

    public void validateAllyPieceAtDestination(final Position destination) {
        if (pieces.stream().anyMatch(piece -> piece.isSamePosition(destination))) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("도착지에 아군 기물이 존재합니다."));
        }
    }

    public void canPieceMoveTo(final Position presentPosition, final Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .ifPresent(piece -> piece.canMoveTo(destination));
    }

    public Positions makeRoute(final Position presentPosition, final Position destination) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .map(piece -> piece.makeRoute(destination))
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.formatMessage("시작 위치에 아군 기물이 존재하지 않습니다.")));
    }

    public int countObstacle(final Positions route) {
        return (int) pieces.stream()
                .filter(route::containsPosition)
                .count();
    }

    public void movePiece(final Position presentPosition, final Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .ifPresent(piece -> piece.updateChessPiecePositionBy(destination));
    }


    public List<Piece> getPieces() {
        return pieces;
    }

    public Boolean isPoAt(final Position presentPosition) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .map(Piece::isPo)
                .orElseThrow();
    }

    public Boolean isExistPoInRoute(final Positions route) {
        return pieces.stream()
                .anyMatch(piece -> route.containsPosition(piece) && piece.isPo());
    }

}
