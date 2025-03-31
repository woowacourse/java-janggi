package janggi.dto;

import janggi.direction.PieceType;
import janggi.piece.players.Team;

public record PieceUpdateDto(Team team, PieceType pieceType, int currentY, int currentX, int arrivalY, int arrivalX) {
}
