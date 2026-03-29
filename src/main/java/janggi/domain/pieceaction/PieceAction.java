package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import java.util.List;

public interface PieceAction {

    List<Position> calculateMovablePositions(final Position from,
        final BoardMediator boardMediator);
}
