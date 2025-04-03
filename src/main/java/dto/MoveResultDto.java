package dto;

import domain.piece.Piece;
import java.util.Optional;

public record MoveResultDto(Piece updatedPiece, Optional<Piece> caughtPiece) {
}
