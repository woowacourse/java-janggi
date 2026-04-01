package domain.stub;

import domain.Board;
import domain.Position;
import domain.piece.Piece;
import java.util.Map;
import strategy.InitializeStrategy;

public class StubBoard extends Board {
    public StubBoard(InitializeStrategy noInitializeStrategy) {
        super(noInitializeStrategy, noInitializeStrategy);
    }

    public void putPieces(Map<Position, Piece> pieces) {
        this.pieces.putAll(pieces);
    }
}
