package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public interface Piece {
    default boolean isCannon() {
        return false;
    }

    List<Route> calculateRoutes(Position position);

    PieceType getType();

    double getScore();

    Team getTeam();
}
