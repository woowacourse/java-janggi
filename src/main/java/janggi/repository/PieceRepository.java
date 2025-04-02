package janggi.repository;

import janggi.GameId;
import janggi.piece.Piece;

import java.sql.Connection;
import java.util.List;

public interface PieceRepository {

    void saveAll(Connection connection, GameId gameId, List<Piece> pieces);

    List<Piece> findAllByGameId(Connection connection, GameId gameId);

    void deleteByGameId(Connection connection, GameId gameId);
}
