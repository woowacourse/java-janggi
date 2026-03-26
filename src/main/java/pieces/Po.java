package pieces;

import movepolicy.destination.DestinationRule;
import movepolicy.path.PathRule;
import movepolicy.destination.PoDestinationRule;
import movepolicy.path.PoPathRule;
import java.util.List;
import position.Position;

public class Po extends PieceImpl {

    public Po(Side side) {
        super(side);
    }

    @Override
    void validateDestination(Position departure, Position destination) {
    }

    @Override
    List<Position> getPathPositions(Position departure, Position destination) {
        // TODO: 이동경로에 있는 포지션들 반환하기
        return List.of();
    }

    @Override
    DestinationRule getDestinationRule() {
        return new PoDestinationRule();
    }

    @Override
    PathRule getPathRule() {
        return new PoPathRule();
    }

    @Override
    public boolean isPo() {
        return true;
    }
}
