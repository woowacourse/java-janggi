package domain.strategy;

import domain.HorseMoveRule;
import domain.Position;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy extends MoveStrategy {

    private Map<Position, List<Position>> moves;

    HorseMoveStrategy(Position position) {
        super(position);
        this.moves = setupDestinationAndRoutesFrom();
    }

    public static HorseMoveStrategy of(Position position) {
        return new HorseMoveStrategy(position);
    }

    @Override
    public void updateRoute() {
        this.moves = setupDestinationAndRoutesFrom();
    }

    private Map<Position, List<Position>> setupDestinationAndRoutesFrom() {
        Map<Position, List<Position>> moves = new HashMap<>();
        Arrays.stream(HorseMoveRule.values())
                .forEach(horseMoveRule ->
                        moves.putIfAbsent(horseMoveRule.destination(position), horseMoveRule.route(position)));
        return moves;
    }

    @Override
    public boolean isMoveAble(Position destination) {
        return moves.containsKey(destination);
    }

    @Override
    public boolean isInvalidPath(Position destination, List<Position> piecePositions) {
        List<Position> route = moves.get(destination);

        return piecePositions.stream().anyMatch(route::contains);
    }
}
