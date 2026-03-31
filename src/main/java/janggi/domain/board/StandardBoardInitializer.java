package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class StandardBoardInitializer implements BoardInitializer {

    private final ElephantFormation hanFormation;
    private final ElephantFormation choFormation;

    public StandardBoardInitializer(ElephantFormation hanFormation, ElephantFormation choFormation) {
        this.hanFormation = hanFormation;
        this.choFormation = choFormation;
    }

    @Override
    public Map<Position, Piece> initialize() {
        Map<Position, Piece> board = new HashMap<>();

        for (InitialPiecePlacement placement : InitialPiecePlacement.values()) {
            placement.placeOn(board);
        }
        board.putAll(hanFormation.placeElephantSetUpPieces());
        board.putAll(choFormation.placeElephantSetUpPieces());
        return board;
    }
}
