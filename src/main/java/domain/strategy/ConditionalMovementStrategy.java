package domain.strategy;

import domain.board.PathPieces;
import java.util.function.Predicate;

public class ConditionalMovementStrategy implements MovementStrategy {

    private final MovementStrategy delegate;
    private final Predicate<PathPieces> precondition;

    public ConditionalMovementStrategy(MovementStrategy delegate, Predicate<PathPieces> precondition) {
        this.delegate = delegate;
        this.precondition = precondition;
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return precondition.test(pathPieces) && delegate.validatePath(pathPieces);
    }
}


