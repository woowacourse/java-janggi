package janggi.dto;

import janggi.domain.piece.Piece;
import janggi.domain.players.Team;
import janggi.domain.piece.position.Position;

public record PieceMove(Team team, Piece piece, Piece caughtPiece,
                        Position currentPosition, Position arrivalPosition, boolean isCaught) {
}
