package object.piece.generator;

import object.coordinate.Coordinate;
import object.coordinate.DiagonalMoveVector;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DiagonalOneInCastlePathGenerator implements PathGenerator {

    @Override
    public Set<Coordinate> generate(Coordinate departure) {
        return Arrays.stream(DiagonalMoveVector.values())
                .map(departure::moveBy)
                .filter(Objects::nonNull)
                .filter(Coordinate::isInCastle)
                .collect(Collectors.toSet());
    }
}
