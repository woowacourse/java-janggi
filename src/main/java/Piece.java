import java.util.List;
import java.util.Map;

public abstract class Piece {
    protected final Dynasty dynasty;

    public Piece(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    public boolean isSameDynasty(Piece piece) {
        if (piece != null && piece.dynasty == this.dynasty) {
            return true;
        }
        return false;
    }

    public abstract List<Dot> getRoute(Dot origin, Dot destination);

    public abstract boolean canMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece);

    public abstract String getName();
}