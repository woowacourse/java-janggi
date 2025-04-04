package janggi.piece.pieces.moverule;

import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public interface MoveRule {
    List<Route> moveAll(Position start);
}
