package dao;

import piece.PieceType;
import piece.Team;

public record PieceEntity(Long id, int rowValue, int columnValue, PieceType pieceType, Team team) {
}
