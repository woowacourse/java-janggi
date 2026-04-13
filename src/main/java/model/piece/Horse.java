package model.piece;

import java.util.List;
import model.Team;
import model.coordinate.Direction;
import model.coordinate.Displacement;
import model.coordinate.Position;

public class Horse extends Piece {

    private static final int HORSE_LONG_STEP = 2;
    private static final int HORSE_SHORT_STEP = 1;

    public Horse(Team team) {
        super(team, PieceType.HORSE);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        if (displacement.isNotStepCombination(HORSE_LONG_STEP, HORSE_SHORT_STEP)) {
            throw new IllegalArgumentException("현재 기물이 이동할 수 없는 위치입니다.");
        }
    }

    @Override
    protected List<Position> extractPath(Position start, Position end) {
        Displacement displacement = end.toDisplacement(start);
        Direction cardinal = displacement.extractCardinal();

        Position step = cardinal.move(start);
        return List.of(step);
    }
}
