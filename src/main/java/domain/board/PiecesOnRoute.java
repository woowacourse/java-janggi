package domain.board;

import domain.Team;
import domain.pieces.Empty;
import domain.pieces.Piece;
import execptions.JanggiGameRuleWarningException;
import java.util.List;

public record PiecesOnRoute(List<Piece> pieces) {

    public boolean hasSameTeamInArrivalPoint(final Team team) {
        final Piece lastPiece = pieces.getLast();
        return lastPiece.hasEqualTeam(team);
    }

    public int count() {
        return (int) pieces.stream()
                .limit(pieces.size() - 1)
                .filter(piece -> !piece.equals(Empty.getInstance()))
                .count();
    }

    public boolean hasNotPieceOnRoute() {
        return count() == 0;
    }

    public boolean canNotJumpOverFirstPiece() {
        return pieces.stream()
                .findFirst()
                .orElseThrow(() -> new JanggiGameRuleWarningException("잘못된 위치입니다."))
                .canNotJumpOver();
    }
}
