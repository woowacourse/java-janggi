package domain;

import domain.piece.PieceType;

public record CellSnapshot(PieceType type, Side side) {
}