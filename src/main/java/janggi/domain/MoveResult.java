package janggi.domain;

import janggi.domain.piece.PieceAttribute;
import janggi.domain.turn.TurnAttribute;

public record MoveResult(TurnAttribute turnAttribute, PieceAttribute pieceAttribute) {
}
