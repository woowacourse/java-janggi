package janggi.dto;

import janggi.move.Piece;
import janggi.piece.players.Team;
import janggi.position.Position;

public record PieceMove(boolean wantRun, Team team, Piece piece, Piece caughtPiece,
                        Position currentPosition, Position arrivalPosition, boolean isCaught) {
}
