package janggi.infra.repository.piece_repository;

import janggi.domain.Country;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Map;

public interface PieceRepository {

    void createTable();

    void deleteTable();

    Map<Country, List<Piece>> findAllPieces(final int number);

    void saveAllPieces(final int number, final Country country, final List<Piece> pieces);
}
