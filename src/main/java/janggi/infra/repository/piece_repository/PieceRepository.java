package janggi.infra.repository.piece_repository;

import janggi.domain.Country;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Map;

public interface PieceRepository {

    void createTable();

    void deleteTable();

    Map<Country, List<Piece>> findAllPieces(int number);

    void saveAllPieces(int number, Country country, List<Piece> pieces);
}
