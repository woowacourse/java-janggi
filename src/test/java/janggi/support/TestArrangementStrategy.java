package janggi.support;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.strategy.ArrangementStrategy;
import janggi.strategy.StrategyLabel;
import java.util.Map;

public class TestArrangementStrategy extends ArrangementStrategy {
    private final Map<Location, Piece> customPieces;
    private final boolean shouldClear;

    public TestArrangementStrategy(Map<Location, Piece> customPieces) {
        this(customPieces, true);
    }

    public TestArrangementStrategy(Map<Location, Piece> customPieces, boolean shouldClear) {
        super(StrategyLabel.HEHE);
        this.customPieces = customPieces;
        this.shouldClear = shouldClear;
    }

    @Override
    public void place(Piece[][] arrangement, Side side) {
        if (shouldClear) {
            clearBoard(arrangement);
        }

        customPieces.forEach((loc, piece) -> {
            arrangement[loc.x()][loc.y()] = piece;
        });
    }

    private void clearBoard(Piece[][] arrangement) {
        for (int row = 0; row < arrangement.length; row++) {
            for (int col = 0; col < arrangement[row].length; col++) {
                arrangement[row][col] = EmptyPiece.getInstance();
            }
        }
    }
}
