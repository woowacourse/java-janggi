package domain.piece;

import domain.board.Direction;
import java.util.Arrays;
import java.util.List;

public class Cha extends SlidingPiece {

    public Cha(Team team) {
        super(team);
    }

    @Override
    public List<Direction> movableDirections() {
        return Arrays.stream(Direction.values()).toList();
    }

    @Override
    public int maxStep() {
        return 10;
    }

    @Override
    public PieceType type() {
        return PieceType.CHA;
    }

    @Override
    public int score() {
        return 13;
    }
}
