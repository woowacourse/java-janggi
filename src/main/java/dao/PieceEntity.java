package dao;

import piece.PieceType;
import piece.Team;

public record PieceEntity(Long id, int rowIndex, int columnIndex, PieceType pieceType, Team team) {
}
