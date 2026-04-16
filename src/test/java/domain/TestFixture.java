package domain;

import domain.board.PieceProvider;
import domain.piece.Blank;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class TestFixture implements PieceProvider {

    private final Map<Position, Piece> pieces = new HashMap<>();

    public void setPiece(Position position, Piece piece) {
        pieces.put(position, piece);
    }

    public void setAllBlank() {
        pieces.clear();
    }

    @Override
    public Piece getPiece(Position position) {
        return pieces.getOrDefault(position, new Blank());
    }

}
