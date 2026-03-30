package domain.board.strategy;

import domain.board.Side;
import domain.coordinate.Position;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;

import java.util.Map;

public class MaSangSangMaStrategy implements InitialStrategy {

    private final Side side;

    public MaSangSangMaStrategy(Side side) {
        this.side = side;
    }

    @Override
    public void apply(Map<Position, Piece> pieceInitPlacements) {
        if (side.isHan()) {
            initStrategyType(pieceInitPlacements, Side.HAN, 0);
        }

        if (side.isChu()) {
            initStrategyType(pieceInitPlacements, Side.CHU, 9);
        }
    }

    private void initStrategyType(Map<Position, Piece> pieceInitPlacements, Side side, int col) {
        pieceInitPlacements.put(new Position(col,1), new Horse(side));
        pieceInitPlacements.put(new Position(col,2), new Elephant(side));
        pieceInitPlacements.put(new Position(col,6), new Elephant(side));
        pieceInitPlacements.put(new Position(col,7), new Horse(side));
    }
}
