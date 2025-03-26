package janggi.repository;

import janggi.PlayingTurn;
import janggi.domain.Coordinate;
import janggi.domain.Piece;
import java.util.Set;

public interface Repository {

    void save(Piece piece);

    void update(Coordinate from, Coordinate to);

    Set<Piece> findAll();

    void deleteByCoordinate(Coordinate coordinate);

    void clear();

    void updateTurn(PlayingTurn playingTurn);

    PlayingTurn getTurn();
}
