package domain.board;

import domain.Team;
import domain.pieces.Empty;
import domain.pieces.Piece;
import execptions.JanggiGameRuleWarningException;
import java.util.List;

public record PieceOnRoute(List<Piece> pieces) {

    public boolean hasArrivalPointInMyTeam(final Team team) {
        final Piece last = pieces.getLast();
        return last.hasEqualTeam(team);
    }

    public int countPieceOnRoute() {
        return (int) pieces.stream()
                .limit(pieces.size() - 1)
                .filter(piece -> !piece.equals(Empty.getInstance()))
                .count();
    }

    public boolean hasNotPieceOnRoute() {
        return countPieceOnRoute() == 0;
    }

    public boolean canNotJumpOverFirstPiece() {
        return pieces.stream()
                .findFirst()
                .orElseThrow(() -> new JanggiGameRuleWarningException("잘못된 위치입니다."))
                .canNotJumpOver();
    }
}
