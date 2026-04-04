package domain.board;

import domain.piece.Camp;
import domain.piece.PieceType;

public record BoardPiece(Position position, Camp camp, PieceType pieceType) {
}
