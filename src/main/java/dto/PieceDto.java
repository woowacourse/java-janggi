package dto;

import domain.piece.PieceType;
import domain.piece.Team;

public record PieceDto(int x, int y, PieceType pieceType, Team team) {
}
