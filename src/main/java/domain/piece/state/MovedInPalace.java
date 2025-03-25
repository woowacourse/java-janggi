package domain.piece.state;

import domain.JanggiPosition;
import domain.pattern.Path;
import domain.pattern.Pattern;
import domain.piece.Side;
import java.util.List;

public abstract class MovedInPalace extends NonContinuousPiece {
    protected final Side side;
    protected final PalaceMovable palaceMovable;

    public MovedInPalace(Side side, PalaceMovable palaceMovable) {
        this.side = side;
        this.palaceMovable = palaceMovable;
    }

    protected void validatePalaceMove(Side side, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        int beforeFile = beforePosition.file();
        int beforeRank = beforePosition.rank();
        int afterFile = afterPosition.file();
        int afterRank = afterPosition.rank();

        if (!palaceMovable.isInPalace(side, afterFile, afterRank)) {
            throw new IllegalArgumentException("궁성 안에서만 이동할 수 있습니다.");
        }
        if (!palaceMovable.passesThroughCenter(side, beforeFile, beforeRank, afterFile, afterRank)) {
            throw new IllegalArgumentException("궁성의 중앙을 지나는 경로로만 이동할 수 있습니다.");
        }
    }

    @Override
    public List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        validatePalaceMove(side, beforePosition, afterPosition);
        return super.findMovablePath(path, beforePosition, afterPosition);
    }
}
