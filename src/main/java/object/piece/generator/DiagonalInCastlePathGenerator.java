package object.piece.generator;

import object.coordinate.Coordinate;
import object.coordinate.DiagonalMoveVector;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DiagonalInCastlePathGenerator implements PathGenerator {

    @Override
    public Set<Coordinate> generate(Coordinate departure) {
        Set<Coordinate> coordinates = new HashSet<>();

        for (DiagonalMoveVector diagonalMoveVector : DiagonalMoveVector.values()) {
            Coordinate current = departure;
            while (true) {
                Coordinate next = current.moveBy(diagonalMoveVector);
                if (Objects.isNull(next) || !next.isInCastle()) {
                    break;
                }
                coordinates.add(next);
                current = next;
            }
        }

        return coordinates;
    }
}
