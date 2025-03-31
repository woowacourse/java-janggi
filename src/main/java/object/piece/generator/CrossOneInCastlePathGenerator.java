package object.piece.generator;

import object.coordinate.Coordinate;
import object.coordinate.CrossMoveVector;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CrossOneInCastlePathGenerator implements PathGenerator {

    @Override
    public Set<Coordinate> generate(Coordinate departure) {
        return Arrays.stream(CrossMoveVector.values())
                .map(departure::moveBy)
                .filter(Objects::nonNull)
                .filter(Coordinate::isInCastle)
                .collect(Collectors.toSet());
    }
}
