package model.piece;

import java.util.List;
import model.Team;
import model.coordinate.Direction;
import model.coordinate.Displacement;
import model.coordinate.Position;

public class Elephant extends Piece {

    private static final int ELEPHANT_LONG_STEP = 3;
    private static final int ELEPHANT_SHORT_STEP = 2;

    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT);
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        if (displacement.isNotStepCombination(ELEPHANT_LONG_STEP, ELEPHANT_SHORT_STEP)) {
            throw new IllegalArgumentException("현재 기물이 이동할 수 없는 위치입니다.");
        }
    }

    @Override
    protected List<Position> extractPath(Position start, Position end) {
        Displacement displacement = end.toDisplacement(start);
        Direction cardinal = displacement.extractCardinal();
        Direction diagonal = displacement.extractDiagonal();

        Position oneStep = cardinal.move(start);
        Position stepping = diagonal.move(oneStep);
        return List.of(oneStep, stepping);
    }
}
