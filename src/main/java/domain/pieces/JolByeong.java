package domain.pieces;

import java.util.List;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.movepolicy.path.PathRule;
import domain.position.Position;

public class JolByeong extends FullPiece {

    public JolByeong(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        List<Position> movableDestinations;
        if (isCho()) {
            movableDestinations = List.of(
                    departure.moveUp(),
                    departure.moveLeft(),
                    departure.moveRight());
        } else {
            movableDestinations = List.of(
                    departure.moveDown(),
                    departure.moveLeft(),
                    departure.moveRight());
        }
        if (!movableDestinations.contains(destination)) {
            throw new IllegalArgumentException("졸병의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        return List.of();
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
        return PieceType.JOL_BYEONG;
    }
}
