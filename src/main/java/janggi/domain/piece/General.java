package janggi.domain.piece;

import static janggi.domain.piece.direction.Direction.DOWN;
import static janggi.domain.piece.direction.Direction.LEFT;
import static janggi.domain.piece.direction.Direction.RIGHT;
import static janggi.domain.piece.direction.Direction.UP;

import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import java.util.List;

public class General extends Piece {

    private static final int GENERAL_SCORE = 0;

    private static final List<List<Direction>> GENERAL_MOVES = List.of(
            List.of(UP),
            List.of(DOWN),
            List.of(LEFT),
            List.of(RIGHT)
    );

    public General(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public double getScore() {
        return GENERAL_SCORE;
    }

    @Override
    protected List<List<Direction>> getMoveStrategy() {
        return GENERAL_MOVES;
    }

    @Override
    public boolean isGeneral() {
        return true;
    }
}
