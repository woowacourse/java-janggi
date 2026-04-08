package boardSetting;

import domain.PieceProvider;
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

    public void setBlank(Position pos) {
        pieces.put(pos, new Blank());
    }

    public void setAllBlank() {
        pieces.clear();
    }

    @Override
    public boolean isBlank(Position position) {
        return !pieces.containsKey(position);
    }

    @Override
    public boolean isCannon(Position position) {
        Piece piece = getPiece(position);
        return piece instanceof domain.piece.Cannon;
    }

    @Override
    public Piece getPiece(Position position) {
        return pieces.getOrDefault(position, new Blank());
    }

}
