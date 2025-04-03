package object.piece.generator;

import java.util.HashSet;
import java.util.Set;
import object.coordinate.Coordinate;
import object.coordinate.DiagonalMoveVector;

public class DiagonalInCastlePathGenerator implements PathGenerator {

    @Override
    public Set<Coordinate> generate(Coordinate departure) {
        Set<Coordinate> coordinates = new HashSet<>();

        for (DiagonalMoveVector vector : DiagonalMoveVector.values()) {
            coordinates.addAll(generateDiagonalCoordinates(departure, vector));
        }

        return coordinates;
    }

    private Set<Coordinate> generateDiagonalCoordinates(Coordinate start, DiagonalMoveVector vector) {
        Set<Coordinate> result = new HashSet<>();
        Coordinate current = start.moveBy(vector);

        while (current != null && current.isInCastle()) {
            result.add(current);
            current = current.moveBy(vector);
        }

        return result;
    }
}
