package domain.strategy;

import domain.moverule.ElephantMoveRule;
import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElephantMoveStrategy extends MoveStrategy {

    private Map<Position, List<Position>> moves;

    public ElephantMoveStrategy(Position position) {
        super(position);
        this.moves = setupDestinationAndRoutes();
    }

    public static ElephantMoveStrategy of(Position position) {
        return new ElephantMoveStrategy(position);
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

    private Map<Position, List<Position>> setupDestinationAndRoutes() {
        return Arrays.stream(ElephantMoveRule.values())
                .collect(Collectors.toMap(
                                elephantMoveRule -> elephantMoveRule.destination(position),
                                elephantMoveRule -> elephantMoveRule.route(position)
                        )
                );
    }
}
