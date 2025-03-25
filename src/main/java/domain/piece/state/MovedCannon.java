package domain.piece.state;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import domain.piece.Piece;
import java.util.List;

public class MovedCannon extends ContinuousPiece {
    @Override
    public List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        return super.findMovablePath(path, beforePosition, afterPosition);
    }

    @Override
    public void validateMove(List<Piece> hurdlePieces) {
        if (hurdlePieces.isEmpty()) {
            throw new IllegalArgumentException("경로에 장애물이 1개 있어야 움직일 수 있습니다.");
        }
        if (hurdlePieces.size() > 1) {
            throw new IllegalArgumentException("경로에 장애물이 2개 이상 있어서 움직일 수 없습니다.");
        }
        if (hurdlePieces.getFirst().getState() instanceof MovedCannon) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }

    @Override
    public PieceState updateState() {
        return new MovedCannon() {
        };
    }
}
