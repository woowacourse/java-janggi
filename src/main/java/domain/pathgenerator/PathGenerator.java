package domain.pathgenerator;

import domain.position.Path;
import domain.position.Position;
import java.util.Optional;
import java.util.Set;

public interface PathGenerator {
    Optional<Path> calculatePath(Position source, Position destination);

    Set<Position> findCandidateDestinations(Position source);
}
