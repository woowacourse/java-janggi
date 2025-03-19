package domain.piece;

import domain.Team;
import java.util.List;

public record PieceOnRoute(List<Piece> pieces) {
    private static final Piece emptyPiece = new EmptyPiece();

    public boolean hasPieceOnRoute() {
        for (int i = 0; i < pieces.size() - 1; i++) {
            if (!pieces.get(i).equals(emptyPiece)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasArrivalPointInMyTeam(Team team) {
        return pieces.getLast().hasEqualTeam(team);
    }
}
