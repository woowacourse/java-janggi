package janggi.dao;

import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.direction.Position;

public class PieceFactory {

    public static Piece createPiece(Position position, Team team, PieceType pieceType) {
        return switch (pieceType) {
            case GENERAL -> new General(position, team);
            case SOLDIER -> new Soldier(position, team);
            case HORSE -> new Horse(position, team);
            case GUARD -> new Guard(position, team);
            case ELEPHANT -> new Elephant(position, team);
            case CHARIOT -> new Chariot(position, team);
            case CANNON -> new Cannon(position, team);
            default -> throw new IllegalArgumentException("알 수 없는 기물: " + pieceType);
        };
    }
}
