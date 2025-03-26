package domain.piece;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.DOWN_LEFT;
import static domain.board.Direction.DOWN_RIGHT;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;

import domain.board.Direction;
import java.util.List;

public class HanByeong extends SlidingPiece {

    public HanByeong(Team team) {
        super(team);
    }

    @Override
    public List<Direction> movableDirections() {
        return List.of(LEFT, DOWN_LEFT, DOWN, DOWN_RIGHT, RIGHT);
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
