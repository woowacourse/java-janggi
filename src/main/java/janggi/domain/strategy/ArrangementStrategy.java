package janggi.domain.strategy;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;

public interface ArrangementStrategy {

    void place(Piece[][] arrangement, PieceFactory pieceFactory);
}
