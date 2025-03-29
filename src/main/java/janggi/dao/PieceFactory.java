package janggi.dao;

import static janggi.dao.PieceType.CANNON;
import static janggi.dao.PieceType.CHARIOT;
import static janggi.dao.PieceType.ELEPHANT;
import static janggi.dao.PieceType.GENERAL;
import static janggi.dao.PieceType.GUARD;
import static janggi.dao.PieceType.HORSE;
import static janggi.dao.PieceType.SOLDIER;

import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.direction.Position;

public class PieceFactory {

    public static PieceType getPieceName(Piece piece) {
        if (piece.getClass() == Soldier.class) {
            return SOLDIER;
        }
        if (piece.getClass() == Guard.class) {
            return GUARD;
        }
        if (piece.getClass() == General.class) {
            return GENERAL;
        }
        if (piece.getClass() == Horse.class) {
            return HORSE;
        }
        if (piece.getClass() == Elephant.class) {
            return ELEPHANT;
        }
        if (piece.getClass() == Chariot.class) {
            return CHARIOT;
        }
        if (piece.getClass() == Cannon.class) {
            return CANNON;
        }
        throw new IllegalArgumentException("알 수 없는 기물입니다.");
    }

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
