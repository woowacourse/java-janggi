package janggi.domain;

import janggi.domain.piece.PieceType;

public record PieceInitInfo(Position position, Side side, PieceType pieceType) {
}
