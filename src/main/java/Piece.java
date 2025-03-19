import java.util.List;
import java.util.Map;

public abstract class Piece {
    protected final Dynasty dynasty;

    public Piece(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    public abstract List<Dot> getRoute(Dot origin, Dot destination);

    public boolean canMove(Map<Dot,Piece> routesWithPiece, Piece destinationPiece) {
        if (destinationPiece != null && destinationPiece.dynasty == this.dynasty) {
            return false;
        }

        return checkRoute(routesWithPiece);
    }

    public abstract boolean checkRoute(Map<Dot,Piece> routesWithPiece);
}