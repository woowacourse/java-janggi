package domain.board;

import domain.Team;
import domain.pieces.Piece;
import execptions.JanggiArgumentException;
import java.util.List;

public record PieceOnRoute(List<Piece> piecesOnRoute, Piece pieceAtArrivalPoint) {

    public boolean hasNotPieceOnRoute() {
        return piecesOnRoute.isEmpty();
    }

    public boolean hasArrivalPointInMyTeam(final Team team) {
        if (pieceAtArrivalPoint == null) {
            return false;
        }
        return pieceAtArrivalPoint.hasEqualTeam(team);
    }

    public int countPieceOnRoute() {
        return piecesOnRoute.size();
    }

    public boolean canNotJumpOverFirstPiece() {
        return piecesOnRoute.stream()
                .findFirst()
                .orElseThrow(() -> new JanggiArgumentException("기물이 없습니다."))
                .canNotJumpOver();
    }
}
