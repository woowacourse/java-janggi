package player;

import java.util.ArrayList;
import movementRule.PieceRule;
import java.util.List;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Pieces {
    private final List<PieceRule> pieceRules;

    public Pieces(final List<PieceRule> pieceRules) {
        this.pieceRules = new ArrayList<>(pieceRules);
    }

    public void removePiece(final Position destination) {
        pieceRules.stream()
                .filter(piece -> piece.isSamePosition(destination))
                .findFirst()
                .ifPresent(pieceRules::remove);
    }

    public boolean hasJanggun() {
        return pieceRules.stream()
                .noneMatch(piece -> piece.getPieceType().isJanggun());
    }

    public void validateAllyPieceAtStart(final Position presentPosition) {
        pieceRules.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.formatMessage("시작 위치에 아군 기물이 존재하지 않습니다.")));
    }

    public void validateAllyPieceAtDestination(final Position destination) {
        if (pieceRules.stream().anyMatch(piece -> piece.isSamePosition(destination))) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("도착지에 아군 기물이 존재합니다."));
        }
    }

    public void canPieceMoveTo(final Position presentPosition, final Position destination) {
        pieceRules.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .ifPresent(piece -> piece.canMoveTo(destination));
    }

    public Positions makeRoute(final Position presentPosition, final Position destination) {
        return pieceRules.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .map(piece -> piece.makeRoute(destination))
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.formatMessage("시작 위치에 아군 기물이 존재하지 않습니다.")));
    }

    public int countObstacle(final Positions route) {
        return (int) pieceRules.stream()
                .filter(route::containsPosition)
                .count();
    }

    public void movePiece(final Position presentPosition, final Position destination) {
        pieceRules.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .ifPresent(piece -> piece.updateChessPiecePositionBy(destination));
    }

    public Boolean isPoAt(final Position presentPosition) {
        return pieceRules.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .map(piece -> piece.getPieceType().isPo())
                .orElseThrow();
    }

    public Boolean isExistPoInRoute(final Positions route) {
        return pieceRules.stream()
                .anyMatch(piece -> route.containsPosition(piece) && piece.getPieceType().isPo());
    }

    public List<PieceRule> getPieces() {
        return pieceRules;
    }

}
