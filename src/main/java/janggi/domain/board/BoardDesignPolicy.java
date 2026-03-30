package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.Map;

public interface BoardDesignPolicy {

    Map<Position, Piece> initBoard();

}
