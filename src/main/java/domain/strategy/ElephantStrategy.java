package domain.strategy;

import domain.ElephantMoveRule;
import domain.Position;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElephantStrategy extends MoveStrategy {

    private final Map<Position, List<Position>> moves;

    public ElephantStrategy(Position position) {
        super(position);
        this.moves = setupDestinationAndRoutesFrom();
    }

    public static ElephantStrategy of(Position position) {
        return new ElephantStrategy(position);
    }

    private Map<Position, List<Position>> setupDestinationAndRoutesFrom() {
        Map<Position, List<Position>> moves = new HashMap<>();
        Arrays.stream(ElephantMoveRule.values())
                .forEach(elephantMoveRule ->
                        moves.putIfAbsent(elephantMoveRule.destination(position), elephantMoveRule.route(position)));
        return moves;
    }

    @Override
    public boolean isMoveAble(Position destination) {
        return moves.containsKey(destination);
    }

    @Override
    public boolean isRouteBlockedBy(Position destination, List<Position> piecePositions) {
        List<Position> route = moves.get(destination);

        return piecePositions.stream().anyMatch(route::contains);
    }
}
