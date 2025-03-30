package janggi.repository;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.service.PlayingTurn;
import java.util.Set;

public interface Repository {

    boolean isConnectable();

    void save(Piece piece);

    void update(Coordinate from, Coordinate to);

    Set<Piece> allPieces();

    void deleteByCoordinate(Coordinate coordinate);

    void clear();

    void updateTurn(PlayingTurn playingTurn);

    PlayingTurn getTurn();
}
