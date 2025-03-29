package janggi.dto;

import janggi.domain.piece.Piece;
import java.util.Optional;

public record PieceDto(Piece moved, Optional<Piece> removed) {
}
