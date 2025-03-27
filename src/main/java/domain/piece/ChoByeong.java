package domain.piece;

import static domain.point.Direction.LEFT;
import static domain.point.Direction.RIGHT;
import static domain.point.Direction.UP;
import static domain.point.Direction.UP_LEFT;
import static domain.point.Direction.UP_RIGHT;

import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Direction;
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
