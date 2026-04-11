package team.janggi.domain.piece;

import team.janggi.domain.Team;

public class PieceFactory {
    public static Piece createPiece(PieceType type, Team team) {
        return switch (type) {
            case KING -> new King(team);
            case GUARD -> new Guard(team);
            case HORSE -> new Horse(team);
            case ELEPHANT -> new Elephant(team);
            case CHARIOT -> new Chariot(team);
            case CANNON -> new Cannon(team);
            case SOLDIER -> new Soldier(team);
            case EMPTY -> Empty.instance;
        };
    }
}
