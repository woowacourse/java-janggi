package domain.piece;

import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;
import static domain.board.Direction.UP_LEFT;
import static domain.board.Direction.UP_RIGHT;

import domain.board.Direction;
import java.util.List;

public class ChoByeong extends SlidingPiece {

    public ChoByeong() {
        super(Team.CHO);
    }

    @Override
    public List<Direction> movableDirections() {
        return List.of(LEFT, UP_LEFT, UP, UP_RIGHT, RIGHT);
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
