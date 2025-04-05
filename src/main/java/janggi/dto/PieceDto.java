package janggi.dto;

import janggi.domain.piece.Piece;
import janggi.domain.players.Team;

public record PieceDto(Team team, Piece piece, int y, int x) {
}
