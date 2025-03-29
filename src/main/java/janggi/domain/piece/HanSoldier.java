package janggi.domain.piece;

import janggi.domain.piece.movepath.FiniteMovePath;
import janggi.domain.piece.movepath.MovePath;
import janggi.domain.piece.movepath.PalaceMovePath;
import java.util.Set;

public class HanSoldier extends Soldier {

    public HanSoldier() {
        super(Dynasty.HAN);
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new FiniteMovePath(Direction.DOWN),
                new FiniteMovePath(Direction.LEFT),
                new FiniteMovePath(Direction.RIGHT),
                new PalaceMovePath(Direction.DOWN_LEFT_DIAGONAL),
                new PalaceMovePath(Direction.DOWN_RIGHT_DIAGONAL)
        );
    }
}
