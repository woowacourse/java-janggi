package domain.dto;

import domain.Team;
import domain.piece.PieceType;

public record PieceDto(int row, int col, Team team, PieceType pieceType) {
}
