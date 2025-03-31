package janggi.domain.piece;

import janggi.domain.piece.movepath.FiniteMovePath;
import janggi.domain.piece.movepath.MovePath;
import janggi.domain.piece.movepath.PalaceMovePath;
import janggi.domain.piece.palace.Palace;
import java.util.Set;

public class ChuSoldier extends Soldier {

    public ChuSoldier() {
        super(Dynasty.CHU);
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new FiniteMovePath(Direction.UP),
                new FiniteMovePath(Direction.LEFT),
                new FiniteMovePath(Direction.RIGHT),
                new PalaceMovePath(Palace.from(dynasty.opposite()), Direction.UP_RIGHT_DIAGONAL),
                new PalaceMovePath(Palace.from(dynasty.opposite()), Direction.UP_LEFT_DIAGONAL));
    }
}
