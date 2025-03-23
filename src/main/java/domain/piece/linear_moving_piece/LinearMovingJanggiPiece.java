package domain.piece.linear_moving_piece;

import static domain.Direction.DOWN;
import static domain.Direction.LEFT;
import static domain.Direction.RIGHT;
import static domain.Direction.UP;

import domain.Pattern;
import domain.piece.JanggiPiece;
import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import domain.Direction;
import java.util.Collections;
import java.util.List;

public abstract class LinearMovingJanggiPiece extends JanggiPiece {

    public LinearMovingJanggiPiece(JanggiSide side, JanggiPieceType type) {
        super(side, type);
    }

    @Override
    public List<Pattern> getRoute(final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        if (afterPosition.rank() == beforePosition.rank()) {
            return setNewPathAndGetAdditionalSizeAboutLeftOrRight(beforePosition, afterPosition);
        }
        if (afterPosition.file() == beforePosition.file()) {
            return setNewPathAndGetAdditionalSizeAboutUpOrDown(beforePosition, afterPosition);
        }
        throw new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다.");
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutLeftOrRight(
            final JanggiPosition beforePosition,
            final JanggiPosition afterPosition
    ) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerFileThan(beforePosition)) {
            newPath = RIGHT;
            additionalSize = afterPosition.getFileGap(beforePosition);
        } else {
            newPath = LEFT;
            additionalSize = afterPosition.getFileGap(beforePosition);
        }
        return createPattern(newPath, additionalSize);
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutUpOrDown(
            final JanggiPosition beforePosition,
            final JanggiPosition afterPosition
    ) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerRankThan(beforePosition)) {
            newPath = DOWN;
            additionalSize = afterPosition.getRankGap(beforePosition);
        } else {
            newPath = UP;
            additionalSize = afterPosition.getRankGap(beforePosition);
        }
        return createPattern(newPath, additionalSize);
    }

    private List<Pattern> createPattern(final Direction newPath, int additionalSize) {
        return Collections.nCopies(additionalSize, type.getRoutes().get(newPath).getFirst());
    }
}
