package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public final class StandardBoardInitializer implements BoardInitializer {

    @Override
    public Map<Position, Piece> initialize() {
        return InitialPiecePlacement.init();
    }
}
