package janggi.support;

import janggi.domain.board.Location;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.strategy.arrangement.ArrangementStrategy;
import java.util.Map;

public class TestArrangementStrategy implements ArrangementStrategy {

    private final Map<Location, Piece> customPieces;

    public TestArrangementStrategy(Map<Location, Piece> customPieces) {
        this.customPieces = customPieces;
    }

    @Override
    public void place(Piece[][] arrangement) {
        for (int row = 0; row < arrangement.length; row++) {
            for (int col = 0; col < arrangement[row].length; col++) {
                arrangement[row][col] = EmptyPiece.getInstance();
            }
        }

        customPieces.forEach((loc, piece) -> {
            arrangement[loc.col()][loc.row()] = piece;
        });
    }
}
