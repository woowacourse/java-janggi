package model.board;

import model.pieces.Piece;
import model.position.Position;

public record PlacedPiece(Position position, Piece piece) {
}
