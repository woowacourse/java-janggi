package piece.validator;

import board.Board;
import coordinate.Coordinate;

public interface PathValidator {

    boolean validate(Board board, Coordinate departure, Coordinate arrival);
}
