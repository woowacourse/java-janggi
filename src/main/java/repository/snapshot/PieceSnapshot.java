package repository.snapshot;

import domain.piece.PieceType;
import domain.piece.Team;

public record PieceSnapshot(int column, int row, PieceType pieceType, Team team) {
}
