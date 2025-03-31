package janggi.dto;

import janggi.direction.PieceType;
import janggi.piece.players.Team;
import janggi.position.Position;

public record PieceMove(boolean wantRun, Team team, PieceType pieceType, PieceType caughtPieceType,
                        Position currentPosition, Position arrivalPosition, boolean isCaught) {
}
