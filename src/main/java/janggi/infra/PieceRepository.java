package janggi.infra;

import janggi.domain.Country;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Map;

public interface PieceRepository {

    Map<Country, List<Piece>> findAllPieces(final int number);

    void saveAllPieces(final int number, final Country country, final List<Piece> pieces);
}
