package janggi.infrastructure;

import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Soldier;

public class PieceFactory {

    private PieceFactory() {
    }

    public static Piece create(String team, String type) {
        PieceType pieceType = PieceType.valueOf(type);
        if (pieceType == PieceType.EMPTY) {
            return new EmptyPiece();
        }
        return createByType(Team.valueOf(team), pieceType);
    }

    private static Piece createByType(Team team, PieceType type) {
        if (type == PieceType.CHARIOT)  return new Chariot(team);
        if (type == PieceType.CANNON)   return new Cannon(team);
        if (type == PieceType.ELEPHANT) return new Elephant(team);
        if (type == PieceType.HORSE)    return new Horse(team);
        if (type == PieceType.GENERAL)  return new General(team);
        if (type == PieceType.GUARD)    return new Guard(team);
        if (type == PieceType.SOLDIER)  return new Soldier(team);
        throw new IllegalArgumentException("[ERROR] 알 수 없는 기물 타입입니다: " + type);
    }

    public static String toTeamString(Piece piece) {
        if (piece.isEmptyPiece()) {
            return "EMPTY";
        }
        if (piece.isSame(Team.HAN)) {
            return "HAN";
        }
        return "CHO";
    }
}
