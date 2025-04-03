package player;

import java.util.HashMap;
import java.util.Map;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class JanggiPan {

    private final Map<Position, PieceType> pieces;

    public JanggiPan(Map<Position, PieceType> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void movePiece(final Position presentPosition, final Position destination) {
        PieceType pieceType = pieces.get(presentPosition);
        pieces.remove(presentPosition);
        pieces.put(destination, pieceType);
    }

    public void validateAllyPieceAtStart(final Position presentPosition) {
        if (!pieces.containsKey(presentPosition)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("시작 위치에 아군 기물이 존재하지 않습니다."));
        }
    }

    public void validateAllyPieceAtDestination(final Position destination) {
        if (pieces.containsKey(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("도착지에 아군 기물이 존재합니다."));
        }
    }

    public void canPieceMoveTo(final Position presentPosition, final Position destination) {
        pieces.get(presentPosition).canMoveTo(presentPosition, destination);
    }

    public int countObstacle(final Positions route) {
        return (int) pieces.keySet().stream()
                .filter(route::contains)
                .count();
    }

    public Positions makeRoute(final Position presentPosition, final Position destination) {
        return pieces.get(presentPosition).makeRoute(presentPosition, destination);
    }

    public boolean hasJanggun() {
        return pieces.values().stream()
                .noneMatch(PieceType::isJanggun);
    }

    public boolean isPoAt(final Position presentPosition) {
        return pieces.get(presentPosition).isPo();
    }

    public boolean isExistPoInRoute(final Positions route) {
        return pieces.keySet().stream()
                .anyMatch(position -> route.contains(position) && pieces.get(position).isPo());
    }

    public Map<Position, PieceType> getPieces() {
        return pieces;
    }

    public void removePiece(Position destination) {
        pieces.remove(destination);
    }
}
