package domain.board;

import domain.Team;
import domain.pieces.EmptyPiece;
import domain.pieces.Piece;
import execptions.JanggiArgumentException;
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

    public boolean hasArrivalPointInMyTeam(final Team team) {
        Piece last = pieces.getLast();
        if (!last.equals(emptyPiece)) {
            return last.hasEqualTeam(team);
        }
        return false;
    }

    public int countPieceOnRoute() {
        int count = 0;
        for (int i = 0; i < pieces.size() - 1; i++) {
            if (!pieces.get(i).equals(emptyPiece)) {
                count++;
            }
        }
        return count;
    }

    public boolean canNotJumpOverFirstPiece() {
        return pieces.stream().filter(piece -> !piece.equals(emptyPiece))
                .findFirst()
                .orElseThrow(() -> new JanggiArgumentException("기물이 없습니다."))
                .canNotJumpOver();
    }
}
