package janggiGame.piece.curveMovePiece;

import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Type;
import java.util.List;
import java.util.function.Function;

public class Horse extends CurveMovePiece {

    public Horse(Dynasty dynasty) {
        super(dynasty, Type.HORSE);
    }

    @Override
    protected List<Function<Dot, Dot>> getMoveSteps(int dx, int dy) {
        return List.of(getFirstMove(dx, dy));
    }

    @Override
    protected boolean isFirstMoveVertical(int dx, int dy) {
        return Math.abs(dx) == 1 && Math.abs(dy) == 2;
    }

    @Override
    protected boolean isFirstMoveHorizontal(int dx, int dy) {
        return Math.abs(dx) == 2 && Math.abs(dy) == 1;
    }
}
