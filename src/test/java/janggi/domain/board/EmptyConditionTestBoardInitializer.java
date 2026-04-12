package janggi.domain.board;

import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Map;

public class EmptyConditionTestBoardInitializer implements BoardInitializer {

    @Override
    public Map<Position, Piece> initialize() {
        return Map.of(new Position(0, 4), new Piece(Camp.HAN, PieceType.CHARIOT));
    }
}
