package model.piece.goongsungpiece;

import model.Team;
import model.piece.PieceInfo;

public class Jang extends GoongsungConstrainedPiece {

    public Jang(Team team) {
        super(team);
        pieceInfo = PieceInfo.JANG;
    }

    @Override
    public boolean isCriticalPiece() {
        return true;
    }

}
