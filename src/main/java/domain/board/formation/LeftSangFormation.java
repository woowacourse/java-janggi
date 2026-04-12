package domain.board.formation;

import domain.state.Side;
import domain.coordinate.Position;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;

import java.util.Map;

public class LeftSangFormation implements InitialFormation {

    private final Side side;

    public LeftSangFormation(Side side) {
        this.side = side;
    }

    @Override
    public void apply(Map<Position, Piece> pieceInitPlacements) {
        initStrategyType(pieceInitPlacements, side, side.getStartingRow());
    }

    private void initStrategyType(Map<Position, Piece> pieceInitPlacements, Side side, int col) {
        pieceInitPlacements.put(new Position(col, 1), new Elephant(side));
        pieceInitPlacements.put(new Position(col, 2), new Horse(side));
        pieceInitPlacements.put(new Position(col, 6), new Elephant(side));
        pieceInitPlacements.put(new Position(col, 7), new Horse(side));
    }
}
