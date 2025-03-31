package janggi.piece;

import janggi.coordinate.Position;
import janggi.player.Team;

public class PieceFactory {

    public static Piece of(final int row, final int column, final PieceType type, final Team team) {
        final Position position = Position.of(row, column);

        return switch (type) {
            case SOLDIER -> Soldier.of(position, team);
            case GUARD -> Guard.of(position, team);
            case ELEPHANT -> Elephant.of(position, team);
            case HORSE -> Horse.of(position, team);
            case CANNON -> Cannon.of(position, team);
            case CHARIOT -> Chariot.of(position, team);
            case GENERAL -> General.of(position, team);
        };
    }
}
