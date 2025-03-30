package janggi.dto;

import janggi.movement.target.AttackedPiece;
import janggi.piece.Piece;

public record MovementDto(Piece movedPiece, AttackedPiece attackedPiece) {
}
