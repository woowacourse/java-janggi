package domain.strategy;

import domain.MoveRoute;
import domain.Position;
import domain.moverule.HorseMoveRule;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HorseMoveStrategy extends MoveStrategy {

    private final Map<Position, List<Position>> moves;

    HorseMoveStrategy(Position position) {
        super(position);
        this.moves = setupDestinationAndRoutes();
    }

    public static HorseMoveStrategy of(Position position) {
        return new HorseMoveStrategy(position);
    }

    @Override
    public boolean isMoveAble(Position destination) {
        return moves.containsKey(destination);
    }

    @Override
    public boolean isPathRestricted(Position destination, List<Position> piecePositions) {
        List<Position> route = moves.get(destination);

        return piecePositions.stream().anyMatch(route::contains);
    }

    private Map<Position, List<Position>> setupDestinationAndRoutes() {
        return HorseMoveRule.moveRoutesOf(position)
                .stream()
                .collect(Collectors.toMap(MoveRoute::destination, MoveRoute::route));
    }
}
