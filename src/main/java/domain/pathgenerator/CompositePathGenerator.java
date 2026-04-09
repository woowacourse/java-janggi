package domain.pathgenerator;

import domain.position.Path;
import domain.position.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Override
    public Set<Position> findCandidateDestinations(Position source) {
        Set<Position> candidateDestinations = new HashSet<>();
        for (PathGenerator pathGenerator : pathGenerators) {
            candidateDestinations.addAll(pathGenerator.findCandidateDestinations(source));
        }
        return candidateDestinations;
    }
}
