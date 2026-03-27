package domain.piece;

import static domain.piece.PieceType.CHA;
import static domain.piece.PieceType.JANG;
import static domain.piece.PieceType.JOL;
import static domain.piece.PieceType.MA;
import static domain.piece.PieceType.PO;
import static domain.piece.PieceType.SA;
import static domain.piece.PieceType.SANG;

import domain.player.Team;

public class PieceFactory {

    public static Piece createPiece(Team team, PieceType pieceType) {
        if (pieceType == CHA) {
            return new Cha(team);
        }
        if (pieceType == MA) {
            return new Ma(team);
        }
        if (pieceType == SA) {
            return new Sa(team);
        }
        if (pieceType == SANG) {
            return new Sang(team);
        }
        if (pieceType == JANG) {
            return new Jang(team);
        }
        if (pieceType == PO) {
            return new Po(team);
        }
        if (pieceType == JOL) {
            return new Jol(team);
        }
        return new None();
    }
}
