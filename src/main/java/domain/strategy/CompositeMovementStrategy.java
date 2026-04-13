package domain.strategy;

import domain.game.Position;
import java.util.List;

public class CompositeMovementStrategy implements MovementStrategy {
    private final List<MovementStrategy> strategies;

    public CompositeMovementStrategy(List<MovementStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return strategies.stream()
                .flatMap(strategy -> strategy.generatePaths(current).stream())
                .toList();
    }
}
