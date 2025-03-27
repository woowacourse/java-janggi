package domain.board;

import domain.pieces.Piece;
import domain.player.TeamType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record PiecesOnRoute(List<Piece> pieces) {

    public boolean hasSameTeamOnArrivalPoint(final TeamType teamType) {
        return Optional.ofNullable(pieces.getLast())
                .map(lastPiece -> lastPiece.hasEqualTeam(teamType))
                .orElse(false);
    }

    public int count() {
        return (int) pieces.stream()
                .limit(pieces.size() - 1)
                .filter(Objects::nonNull)
                .count();
    }

    public boolean hasNotPieceOnRoute() {
        return count() == 0;
    }

    public boolean canNotJumpOverFirstPiece() {
        return pieces.stream().anyMatch(p -> p != null && p.canNotJumpOver());
    }
}
