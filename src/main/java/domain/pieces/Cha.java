package domain.pieces;

import java.util.List;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.movepolicy.path.PathRule;
import domain.movement.Direction;
import domain.position.Position;
import domain.movement.SlidingDirectionFinder;
import domain.movement.SlidingPath;

public class Cha extends FullPiece {
    private static final SlidingDirectionFinder SLIDING_DIRECTION_FINDER = new SlidingDirectionFinder();

    public Cha(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (!departure.isSameRow(destination) && !departure.isSameColumn(destination)) {
            throw new IllegalArgumentException("차의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        Direction direction = SLIDING_DIRECTION_FINDER.find(departure, destination);
        return new SlidingPath(direction).pathPositions(departure, destination);
    }

    @Override
    protected DestinationRule getDestinationRule() {
        return new BasicDestinationRule();
    }

    @Override
    protected PathRule getPathRule() {
        return new EmptyPathRule();
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.CHA;
    }
}
