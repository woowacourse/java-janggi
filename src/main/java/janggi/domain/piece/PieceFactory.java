package janggi.domain.piece;

import janggi.domain.status.Team;

public class PieceFactory {

    public static Piece of (Team team, String pieceName) {
        PieceType pieceType = PieceType.valueOf(pieceName);
        if (pieceType.equals(PieceType.PHO)) {
            return new Pho(team);
        }
        if (pieceType.equals(PieceType.MA)) {
            return new Ma(team);
        }
        if (pieceType.equals(PieceType.SANG)) {
            return new Sang(team);
        }
        if (pieceType.equals(PieceType.CHA)) {
            return new Cha(team);
        }
        if (pieceType.equals(PieceType.JOL)) {
            return new Jol(team);
        }
        if (pieceType.equals(PieceType.SA)) {
            return new Sa(team);
        }
        if (pieceType.equals(PieceType.JANG)) {
            return new Jang(team);
        }
        return null;
    }
}
