package domain.piece;

import static domain.point.Direction.DOWN;
import static domain.point.Direction.DOWN_LEFT;
import static domain.point.Direction.DOWN_RIGHT;
import static domain.point.Direction.LEFT;
import static domain.point.Direction.RIGHT;

import domain.PieceType;
import domain.Team;
import domain.point.Direction;
import java.util.List;

public class HanByeong extends SlidingPiece {

    public HanByeong() {
        super(Team.HAN);
    }

    @Override
    public List<Direction> movableDirections() {
        return List.of(LEFT, DOWN_LEFT, DOWN, DOWN_RIGHT, RIGHT);
    }

    @Override
    public boolean isOnlyMovableInPalace() {
        return false;
    }

    @Override
    public int maxStep() {
        return 1;
    }

    @Override
    public PieceType type() {
        return PieceType.BYEONG;
    }

    @Override
    public int score() {
        return 2;
    }
}
