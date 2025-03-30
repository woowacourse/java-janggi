package piece.generator;

import coordinate.Coordinate;
import java.util.Set;

public interface PathGenerator {

    Set<Coordinate> generate(Coordinate departure);
}
