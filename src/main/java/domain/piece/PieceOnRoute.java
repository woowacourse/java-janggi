package domain.piece;

import domain.Team;
import java.util.List;

public record PieceOnRoute(List<Piece> pieces) {
    private static final Piece emptyPiece = new EmptyPiece();

    public boolean hasNotPieceOnRoute() {
        for (int i = 0; i < pieces.size() - 1; i++) {
            if (!pieces.get(i).equals(emptyPiece)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasArrivalPointInMyTeam(Team team) {
        Piece last = pieces.getLast();
        if (!last.equals(emptyPiece)) {
            return last.hasEqualTeam(team);
        }
        return false;
    }
}
