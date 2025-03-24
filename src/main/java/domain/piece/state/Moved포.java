package domain.piece.state;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import domain.piece.Piece;
import java.util.List;

public class Moved포 extends ContinuousPiece {
    @Override
    public List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        return super.findMovablePath(path, beforePosition, afterPosition);
    }

    @Override
    public void validateMove(List<Piece> hurdlePieces) {
        if (hurdlePieces.size() > 1) {
            throw new IllegalStateException("경로에 장애물이 2개 이상 있어서 움직일 수 없습니다.");
        }
        if (hurdlePieces.getFirst().getState() instanceof Moved포) {
            throw new IllegalStateException("포는 포를 넘을 수 없습니다.");
        }
    }

    @Override
    public PieceState updateState() {
        return new Moved포() {
        };
    }
}
