package object.piece.validator;

import object.board.Board;
import object.coordinate.Coordinate;

public interface PathValidator {

    boolean validate(Board board, Coordinate departure, Coordinate arrival);
}
