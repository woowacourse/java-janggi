package domain.piece;

import static domain.point.Direction.DOWN;
import static domain.point.Direction.DOWN_LEFT;
import static domain.point.Direction.DOWN_RIGHT;
import static domain.point.Direction.LEFT;
import static domain.point.Direction.RIGHT;
import static domain.point.Direction.UP;
import static domain.point.Direction.UP_LEFT;
import static domain.point.Direction.UP_RIGHT;

import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Direction;
import java.util.List;

public class Byeong extends SlidingPiece {

    public Byeong(Team team) {
        super(team);
    }

    @Override
    public boolean isOnlyMovableInPalace() {
        return false;
    }

    @Override
    public PieceType type() {
        return PieceType.BYEONG;
    }

    @Override
    public int score() {
        return 0;
    }

    @Override
    public List<Direction> movableDirections() {
        return switch (team()) {
            case CHO -> List.of(LEFT, UP_LEFT, UP, UP_RIGHT, RIGHT);
            case HAN -> List.of(LEFT, DOWN_LEFT, DOWN, DOWN_RIGHT, RIGHT);
        };
    }

    @Override
    public int maxStep() {
        return 1;
    }
}
