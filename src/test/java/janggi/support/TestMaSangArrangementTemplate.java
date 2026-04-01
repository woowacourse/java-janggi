package janggi.support;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.strategy.MaSangArrangementTemplate;
import java.util.Map;

public class TestMaSangArrangementTemplate extends MaSangArrangementTemplate {
    private final Map<Location, Piece> customPieces;

    public TestMaSangArrangementTemplate(Map<Location, Piece> customPieces) {
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
