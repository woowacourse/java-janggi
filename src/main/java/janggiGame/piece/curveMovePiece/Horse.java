package janggiGame.piece.curveMovePiece;

import janggiGame.board.Dot;

import janggiGame.piece.Dynasty;
import java.util.List;
import java.util.function.Function;

public class Horse extends CurveMovePiece {
    private static final String NAME = "마";

    public Horse(Dynasty dynasty) {
        super(dynasty);
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

    @Override
    public String getName() {
        return NAME;
    }
}
