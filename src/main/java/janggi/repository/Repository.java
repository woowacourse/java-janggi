package janggi.repository;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.game.PlayingTurn;
import java.util.Collection;
import java.util.Set;

public interface Repository {

    boolean isConnectable();

    void saveAll(Collection<Piece> pieces);

    void update(Coordinate from, Coordinate to, final PlayingTurn playingTurn);

    Set<Piece> allPieces();

    void clear();

    PlayingTurn getPlayingTurn();
}
