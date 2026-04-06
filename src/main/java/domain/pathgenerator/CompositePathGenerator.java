package domain.pathgenerator;

import common.JanggiException;
import domain.position.Path;
import domain.position.Position;
import java.util.List;

public class CompositePathGenerator implements PathGenerator {

    private static final String INVALID_MOVEMENT = "기물의 이동규칙에 어긋납니다.";

    private final List<PathGenerator> pathGenerators;

    public CompositePathGenerator(List<PathGenerator> pathGenerators) {
        this.pathGenerators = List.copyOf(pathGenerators);
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        for (PathGenerator pathGenerator : pathGenerators) {
            if (pathGenerator.isPathPossible(source, destination)) {
                return pathGenerator.calculatePath(source, destination);
            }
        }
        throw new JanggiException(INVALID_MOVEMENT);
    }

    @Override
    public boolean isPathPossible(Position source, Position destination) {
        return pathGenerators.stream()
                .anyMatch(pathGenerator -> pathGenerator.isPathPossible(source, destination));
    }
}
