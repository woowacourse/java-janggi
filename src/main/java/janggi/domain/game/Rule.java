package janggi.domain.game;

import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Collection;

public interface Rule {
    boolean isEnd(Collection<Piece> pieces);

    Side getWinSide(Collection<Piece> pieces);
}
