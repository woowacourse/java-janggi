package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class HorseStrategy implements MoveStrategy {
    private HorseStrategy() {
    }

    public static HorseStrategy getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    @Override
    public Paths findMovablePaths(Position current) {
        return new Paths(Stream.of(
                        createPath(current, Direction.UP, Direction.UP_RIGHT),
                        createPath(current, Direction.UP, Direction.UP_LEFT),
                        createPath(current, Direction.DOWN, Direction.DOWN_RIGHT),
                        createPath(current, Direction.DOWN, Direction.DOWN_LEFT),
                        createPath(current, Direction.RIGHT, Direction.UP_RIGHT),
                        createPath(current, Direction.RIGHT, Direction.DOWN_RIGHT),
                        createPath(current, Direction.LEFT, Direction.UP_LEFT),
                        createPath(current, Direction.LEFT, Direction.DOWN_LEFT)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList());
    }

    public Optional<Path> createPath(Position current, Direction straight, Direction diagonal) {
        return current.move(straight)
                .flatMap(wp -> wp.move(diagonal)
                        .map(dest -> Path.of(List.of(wp), dest)));
    }

    private static class SingleInstanceHolder {
        private static final HorseStrategy INSTANCE = new HorseStrategy();
    }
}
