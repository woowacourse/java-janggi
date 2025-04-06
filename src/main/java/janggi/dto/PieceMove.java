package janggi.dto;

import janggi.domain.piece.Piece;
import janggi.domain.piece.position.Position;
import janggi.domain.players.Team;
import java.util.Optional;

public record PieceMove(Team team, Piece piece, Position from, Position to, Optional<Piece> caughtPiece) {

    public PieceMove(final Team team, final Piece piece, final Position currentPosition,
                     final Position arrivalPosition) {
        this(team, piece, currentPosition, arrivalPosition, Optional.empty());
    }

    public static PieceMove capture(final Team team, final Piece piece, final Position from, final Position to,
                                    final Piece capturedPiece) {
        return new PieceMove(team, piece, from, to, Optional.ofNullable(capturedPiece));
    }

    public boolean isNotCaptured() {
        return caughtPiece.isEmpty();
    }
}
