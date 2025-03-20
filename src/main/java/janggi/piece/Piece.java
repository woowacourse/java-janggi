package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

public interface Piece {
    Position getPosition();

    String getName();

    Team getTeam();

    boolean isOccupiedByMe(Position position);
}
