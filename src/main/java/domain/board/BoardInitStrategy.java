package domain.board;

import domain.Coordinate;
import domain.piece.Piece;
import java.util.Map;

@FunctionalInterface
public interface BoardInitStrategy {

    Map<Coordinate, Piece> initialize();
}
