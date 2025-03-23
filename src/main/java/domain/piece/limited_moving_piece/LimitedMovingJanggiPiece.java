package domain.piece.limited_moving_piece;

import domain.Pattern;
import domain.piece.JanggiPiece;
import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.Map.Entry;

public abstract class LimitedMovingJanggiPiece extends JanggiPiece {

    public LimitedMovingJanggiPiece(JanggiSide side, JanggiPieceType type) {
        super(side, type);
    }

    @Override
    public List<Pattern> getRoute(final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        return type.getRoutes().entrySet().stream()
                .filter(entry -> {
                    List<Pattern> patterns = entry.getValue();
                    if (beforePosition.canMove(patterns)) {
                        JanggiPosition newPosition = beforePosition.move(patterns);
                        return newPosition.equals(afterPosition);
                    }
                    return false;
                })
                .findFirst()
                .map(Entry::getValue)
                .orElseThrow(() -> new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다."));
    }
}
