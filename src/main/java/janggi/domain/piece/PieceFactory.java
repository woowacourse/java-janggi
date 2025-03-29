package janggi.domain.piece;

import janggi.domain.Team;

public class PieceFactory {

    public static Piece createPiece(PieceType pieceType, Position position, Team team) {
        return switch (pieceType) {
            case PieceType.CANNON -> new Cannon(position, team);
            case PieceType.CHARIOT -> new Chariot(position, team);
            case PieceType.ELEPHANT -> new Elephant(position, team);
            case PieceType.GENERAL -> new General(position, team);
            case PieceType.GUARD -> new Guard(position, team);
            case PieceType.HORSE -> new Horse(position, team);
            case PieceType.SOLDIER -> new Soldier(position, team);
            case PieceType.NONE -> new None(position);
        };
    }
}
