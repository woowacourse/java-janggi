package util;

import domain.PieceType;
import domain.Team;
import domain.piece.Cannon;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Rook;

public class PieceFactory {
    public static Piece createPiece(PieceType pieceType, Team team) {
        if (pieceType == PieceType.ROOK) {
            return new Rook(team);
        }
        if (pieceType == PieceType.CANNON) {
            return new Cannon(team);
        }
        if (pieceType == PieceType.KING) {
            return new King(team);
        }
        if (pieceType == PieceType.PAWN) {
            return new Pawn(team);
        }
        if (pieceType == PieceType.ELEPHANT) {
            return new Elephant(team);
        }
        if (pieceType == PieceType.HORSE) {
            return new Horse(team);
        }
        if (pieceType == PieceType.GUARD) {
            return new Guard(team);
        }
        throw new IllegalArgumentException("존재하지 않는 기물입니다.");
    }
}
