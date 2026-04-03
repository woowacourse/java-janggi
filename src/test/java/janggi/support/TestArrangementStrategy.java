package janggi.support;

import janggi.domain.Location;
import janggi.domain.piece.Piece;
import janggi.strategy.ArrangementStrategy;
import java.util.Map;

public class TestArrangementStrategy implements ArrangementStrategy {
    private final Map<Location, Piece> customPieces;

    public TestArrangementStrategy(Map<Location, Piece> customPieces) {
        this.customPieces = customPieces;
    }

    @Override
    public void place(Piece[][] arrangement) {
        placeVariablePieces(arrangement);
    }

    protected void placeVariablePieces(Piece[][] arrangement) {
        customPieces.forEach((loc, piece) -> {
            arrangement[loc.x()][loc.y()] = piece;
        });
    }
}
