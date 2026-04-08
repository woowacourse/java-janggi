package janggi.domain.strategy.arrangement;

import janggi.domain.board.Location;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import java.util.Map;

public class DBArrangementStrategy implements ArrangementStrategy {

    private final Map<Location, Piece> pieces;

    public DBArrangementStrategy(Map<Location, Piece> pieces) {
        this.pieces = pieces;
    }

    @Override
    public void place(Piece[][] arrangement) {
        for (int row = 0; row < arrangement.length; row++) {
            for (int col = 0; col < arrangement[row].length; col++) {
                arrangement[row][col] = EmptyPiece.getInstance();
            }
        }

        pieces.forEach((loc, piece) ->
                arrangement[loc.row()][loc.col()] = piece
        );
    }
}
