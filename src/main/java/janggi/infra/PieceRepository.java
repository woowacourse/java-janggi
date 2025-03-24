package janggi.infra;

import janggi.domain.piece.Piece;

import java.util.List;

public interface PieceRepository {

    List<Piece> findAllPieces(final int number);

    void saveAllPieces(final int number, final List<Piece> pieces);
}
