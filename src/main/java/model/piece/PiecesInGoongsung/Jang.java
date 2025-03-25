package model.piece.PiecesInGoongsung;

import model.Team;
import model.piece.PieceName;

public class Jang extends PiecesInGoongsung {

    public Jang(Team team) {
        super(team);
        pieceName = PieceName.JANG;
    }

    @Override
    public boolean isCriticalPiece() {
        return true;
    }

}
