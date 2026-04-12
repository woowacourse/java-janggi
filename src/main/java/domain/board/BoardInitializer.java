package domain.board;

import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.Map;

public interface BoardInitializer {

    Map<Position, Piece> initialize();
}
