package domain.piece;

import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Direction;
import java.util.Arrays;
import java.util.List;

public class Sa extends SlidingPiece {

    public Sa(Team team) {
        super(team);
    }

    @Override
    public List<Direction> movableDirections() {
        return Arrays.stream(Direction.values()).toList();
    }

    @Override
    public boolean isOnlyMovableInPalace() {
        return true;
    }

    @Override
    public int maxStep() {
        return 1;
    }

    @Override
    public PieceType type() {
        return PieceType.SA;
    }

    @Override
    public int score() {
        return 3;
    }
}
