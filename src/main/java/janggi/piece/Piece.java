package janggi.piece;

import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public interface Piece {

    List<Route> calculateRoutes(Position position);
}
