package janggi.repository;

import janggi.piece.Piece;

import java.util.List;

public interface PieceRepository {

    void saveAll(long gameId, List<Piece> pieces);

    List<Piece> findAllByGameId(long gameId);
}
