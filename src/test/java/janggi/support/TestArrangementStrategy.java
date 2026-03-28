package janggi.support;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.strategy.ArrangementStrategy;
import janggi.strategy.StrategyLabel;
import java.util.Map;

public class TestArrangementStrategy extends ArrangementStrategy {
    private final Map<Location, Piece> customPieces;

    public TestArrangementStrategy(Map<Location, Piece> customPieces) {
        super(StrategyLabel.MSMS);
        this.customPieces = customPieces;
    }

    @Override
    public void place(Piece[][] arrangement, Side side) {
        placeVariablePieces(arrangement, side);
    }

    @Override
    protected void placeVariablePieces(Piece[][] arrangement, Side side) {
        customPieces.forEach((loc, piece) -> {
            arrangement[loc.x()][loc.y()] = piece;
        });
    }
}
