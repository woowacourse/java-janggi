package domain.board;

import domain.coordination.Coordination;
import domain.piece.Piece;

import java.util.Map;

public interface PlacementStrategy {
    Map<Coordination, Piece> place();
}
