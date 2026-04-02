package domain.pathgenerator;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class DirectionalPathGenerator implements PathGenerator {

    @Override
    public final Path calculatePath(Position source, Position destination) {
        validateMove(source, destination);
        Direction direction = determineDirection(source, destination);
        return buildPath(source, destination, direction);
    }

    protected abstract void validateMove(Position source, Position destination);

    protected abstract Direction determineDirection(Position source, Position destination);

    protected Path buildPath(Position source, Position destination, Direction direction) {
        List<Position> path = new ArrayList<>();
        Position current = source;

        while (!current.equals(destination)) {
            current = direction.calculateNextPosition(current);
            path.add(current);
        }

        path.removeLast();
        return new Path(source, destination, path);
    }
}

