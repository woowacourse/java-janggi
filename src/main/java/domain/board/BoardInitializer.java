package domain.board;

import domain.coordinate.Position;
import domain.Side;
import domain.piece.Piece;

import java.util.Map;

public interface BoardInitializer {

    Map<Position, Piece> initialize();

    Side getFirstTurnSide();
}
