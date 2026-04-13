package domain.board;

import domain.piece.Piece;
import domain.position.Position;

public record BoardMove(Position source, Position destination, Piece movedPiece) {
}
