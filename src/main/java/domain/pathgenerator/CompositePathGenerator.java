package domain.pathgenerator;

import domain.position.Path;
import domain.position.Position;
import java.util.List;
import java.util.Optional;

public class CompositePathGenerator implements PathGenerator {

    private final List<PathGenerator> pathGenerators;

    public CompositePathGenerator(List<PathGenerator> pathGenerators) {
        this.pathGenerators = List.copyOf(pathGenerators);
    }

    @Override
    public Optional<Path> calculatePath(Position source, Position destination) {
        for (PathGenerator pathGenerator : pathGenerators) {
            Optional<Path> optionalPath = pathGenerator.calculatePath(source, destination);
            if (optionalPath.isPresent()) {
                return optionalPath;
            }
        }
        return Optional.empty();
    }
}
