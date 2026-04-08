package janggi.repositiory.piece;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;

public record PieceData(int gameId, int row, int col, PieceType type, Team team) {
}
