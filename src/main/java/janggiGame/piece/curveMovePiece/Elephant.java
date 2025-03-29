package janggiGame.piece.curveMovePiece;

import janggiGame.position.Position;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Type;
import java.util.List;
import java.util.function.Function;

public class Elephant extends CurveMovePiece {

    public Elephant(Dynasty dynasty) {
        super(dynasty, Type.ELEPHANT);
    }

    @Override
    protected List<Function<Position, Position>> getMoveSteps(int dx, int dy) {
        return List.of(
                getFirstMove(dx, dy),
                getDiagonalMove(dx, dy)
        );
    }

    private Function<Position, Position> getDiagonalMove(int dx, int dy) {
        if (dx > 0 && dy > 0) {
            return Position::upRight;
        }
        if (dx > 0) {
            return Position::downRight;
        }
        if (dy > 0) {
            return Position::upLeft;
        }
        return Position::downLeft;
    }

    @Override
    protected boolean isFirstMoveVertical(int dx, int dy) {
        return Math.abs(dx) == 2 && Math.abs(dy) == 3;
    }

    @Override
    protected boolean isFirstMoveHorizontal(int dx, int dy) {
        return Math.abs(dx) == 3 && Math.abs(dy) == 2;
    }
}
