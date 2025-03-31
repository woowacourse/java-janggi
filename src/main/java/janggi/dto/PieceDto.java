package janggi.dto;

import janggi.direction.PieceType;
import janggi.piece.players.Team;

public record PieceDto(Team team, PieceType pieceType, int y, int x) {
}
