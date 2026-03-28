package domain.strategy;

import domain.HorseMoveRule;
import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public boolean isMoveAble(Position destination) {
        return moves.containsKey(destination);
    }

    @Override
    public boolean hasPieceOnPath(Position destination, List<Position> piecePositions) {
        List<Position> route = moves.get(destination);

        return piecePositions.stream().anyMatch(route::contains);
    }

    private Map<Position, List<Position>> setupDestinationAndRoutesFrom() {
        return Arrays.stream(HorseMoveRule.values())
                .collect(Collectors.toMap(
                        horseMoveRule -> horseMoveRule.destination(position),
                        horseMoveRule -> horseMoveRule.route(position)
                        )
                );
    }
}
