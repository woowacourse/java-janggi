package model.piece.goongsungpiece;

import model.Team;
import model.piece.PieceName;

public class Jang extends GoongsungPiece {

    public Jang(Team team) {
        super(team);
        pieceName = PieceName.JANG;
    }

    @Override
    public boolean isCriticalPiece() {
        return true;
    }

}
