package dto;

import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.Map;

public record BoardDto(Map<Position, Piece> board) {
}
