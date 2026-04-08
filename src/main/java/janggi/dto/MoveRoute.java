package janggi.dto;

import janggi.domain.piece.PieceType;
import java.util.List;
import java.util.Optional;

public record MoveRoute(
    List<PieceType> intermediatePieceTypes,
    Optional<PieceType> targetPieceType
) {

}
