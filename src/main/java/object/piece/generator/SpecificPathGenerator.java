package object.piece.generator;

import object.coordinate.Coordinate;
import object.coordinate.MoveVector;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SpecificPathGenerator implements PathGenerator {

    private final List<List<MoveVector>> moveVectors;

    public SpecificPathGenerator(List<List<MoveVector>> moveVectors) {
        this.moveVectors = moveVectors;
    }

    @Override
    public Set<Coordinate> generate(Coordinate departure) {
        return moveVectors.stream()
                .map(departure::moveBy)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
