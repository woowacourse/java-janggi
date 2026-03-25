package janggi.domain.piece;

import janggi.domain.status.Team;

public class PieceFactory {

    public static Piece of (Team team, String pieceName) {
        if (pieceName.equals("Pho")) {
            return new Pho(team, pieceName);
        }
        if (pieceName.equals("Ma")) {
            return new Ma(team, pieceName);
        }
        if (pieceName.equals("Sang")) {
            return new Sang(team, pieceName);
        }
        if (pieceName.equals("Cha")) {
            return new Cha(team, pieceName);
        }
        return null;
    }
}
