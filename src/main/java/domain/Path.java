package domain;

import domain.board.Position;
import domain.piece.Piece;

public record Path(Position position, Piece piece) {
}
