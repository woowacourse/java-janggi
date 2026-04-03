package janggi.domain.board.initializer;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public interface BoardInitializer {

    Map<Position, Piece> initialize();
}
