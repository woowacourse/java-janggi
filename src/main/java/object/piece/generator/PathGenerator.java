package object.piece.generator;

import object.coordinate.Coordinate;
import java.util.Set;

public interface PathGenerator {

    Set<Coordinate> generate(Coordinate departure);
}
