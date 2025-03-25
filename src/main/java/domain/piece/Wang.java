package domain.piece;

import domain.board.Direction;
import java.util.Arrays;
import java.util.List;

public class Wang extends SlidingPiece {

    public Wang(Team team) {
        super(team);
    }

    @Override
    public List<Direction> movableDirections() {
        return Arrays.stream(Direction.values()).toList();
    }

    @Override
    public int step() {
        return 1;
    }

    @Override
    public PieceType type() {
        return PieceType.WANG;
    }

    @Override
    public int score() {
        return 0;
    }
}
