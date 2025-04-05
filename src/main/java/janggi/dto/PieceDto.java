package janggi.dto;

import janggi.move.Piece;
import janggi.piece.players.Team;

public record PieceDto(Team team, Piece piece, int y, int x) {
}
