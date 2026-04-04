package janggi.dto;

import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;

public record PieceDto(PieceType pieceType, TeamType teamType) {
}