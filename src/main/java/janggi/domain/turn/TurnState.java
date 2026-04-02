package janggi.domain.turn;

import janggi.domain.piece.PieceAttribute;

public record TurnState(PlayerTurn playerTurn, TurnAttribute turnAttribute, PieceAttribute movedPiece) {
}
