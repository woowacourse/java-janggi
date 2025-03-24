package domain.board;

import domain.Team;
import domain.pieces.Piece;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record PiecesOnRoute(List<Piece> pieces) {

    public boolean hasSameTeamInArrivalPoint(final Team team) {
        return Optional.ofNullable(pieces.getLast())
                .map(lastPiece -> lastPiece.hasEqualTeam(team))
                .orElse(false);
    }

    public int count() {
        return (int) pieces.stream()
                .filter(Objects::nonNull)
                .limit(pieces.size() - 1)
                .count();
    }

    public boolean hasNotPieceOnRoute() {
        return count() == 0;
    }

    public boolean canNotJumpOverFirstPiece() {
        return pieces.stream().findFirst()
                .map(Piece::canNotJumpOver)
                .orElse(false);
    }
}
