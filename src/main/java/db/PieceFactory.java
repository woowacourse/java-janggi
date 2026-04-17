package db;

import domain.Team;
import domain.piece.*;

public class PieceFactory {

    public static Piece create(PieceType pieceType, Team team) {
        if (pieceType == PieceType.CAR) {
            return new Car(team);
        }
        if (pieceType == PieceType.HORSE) {
            return new Horse(team);
        }
        if (pieceType == PieceType.ELEPHANT) {
            return new Elephant(team);
        }
        if (pieceType == PieceType.GUARD) {
            return new Guard(team);
        }
        if (pieceType == PieceType.KING) {
            return new King(team);
        }
        if (pieceType == PieceType.CANNON) {
            return new Cannon(team);
        }
        if (pieceType == PieceType.PAWN) {
            return new Pawn(team);
        }
        throw new IllegalArgumentException("[ERROR] 알 수 없는 기물 타입: " + pieceType);
    }
}
