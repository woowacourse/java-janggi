package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Destinations;
import java.util.List;

public class ElephantMoveStrategy extends PieceStrategy {

    @Override
    protected Destinations navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        if (!baseDir.canMove(current)) {
            return new Destinations();
        }
        Position firstStep = baseDir.move(current);
        if (!boardInfo.isEmpty(firstStep)) {
            return new Destinations();
        }
        List<Direction> nextDirections = baseDir.nextDiagonalDirections();
        return navigationIfEnemy(current, nextDirections, firstStep, boardInfo);
    }

    private Destinations navigationIfEnemy(Position current, List<Direction> nextDirections, Position firstStep,
                                           BoardInfo boardInfo) {
        Destinations destinations = new Destinations();
        for (Direction direction : nextDirections) {
            destinations = destinations.addDestinations(navigationIfEnemy(current, direction, firstStep, boardInfo));
        }
        return destinations;
    }

    private Destinations navigationIfEnemy(Position current, Direction targetDirection, Position firstStep,
                                   BoardInfo boardInfo) {
        if (!targetDirection.canMove(firstStep) || !boardInfo.isEmpty(targetDirection.move(firstStep))) {
            return new Destinations();
        }
        Position nextStep = targetDirection.move(firstStep);
        if (targetDirection.canMove(nextStep) &&
                !boardInfo.isAlly(current, targetDirection.move(nextStep))) {
            return new Destinations(List.of(targetDirection.move(nextStep)));
        }
        return new Destinations();
    }
}
