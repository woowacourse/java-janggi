package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Destinations;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import java.util.EnumSet;
import java.util.List;

public class ElephantMoveStrategy extends PieceStrategy {

    @Override
    protected Destinations navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        if (!baseDir.canMove(current)) {
            return Destinations.empty();
        }
        Position firstStep = baseDir.move(current);
        if (!boardInfo.isEmpty(firstStep)) {
            return Destinations.empty();
        }
        EnumSet<Direction> nextDirections = baseDir.nextDiagonalDirections();
        return navigationIfEnemy(current, nextDirections, firstStep, boardInfo);
    }

    private Destinations navigationIfEnemy(Position current, EnumSet<Direction> nextDirections, Position firstStep,
                                           BoardInfo boardInfo) {
        Destinations destinations = Destinations.empty();
        for (Direction direction : nextDirections) {
            destinations = destinations.addDestinations(navigationIfEnemy(current, direction, firstStep, boardInfo));
        }
        return destinations;
    }

    private Destinations navigationIfEnemy(Position current, Direction targetDirection, Position firstStep,
                                           BoardInfo boardInfo) {
        if (!targetDirection.canMove(firstStep) || !boardInfo.isEmpty(targetDirection.move(firstStep))) {
            return Destinations.empty();
        }
        Position nextStep = targetDirection.move(firstStep);
        if (targetDirection.canMove(nextStep) &&
                !boardInfo.isAlly(current, targetDirection.move(nextStep))) {
            return Destinations.of(List.of(targetDirection.move(nextStep)));
        }
        return Destinations.empty();
    }
}
