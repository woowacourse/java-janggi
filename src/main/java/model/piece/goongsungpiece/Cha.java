package model.piece.goongsungpiece;

import java.util.Map;
import model.Team;
import model.piece.Piece;
import model.piece.PieceInfo;

public class Cha extends GoongsungAdvantagePiece {

    public Cha(Team team) {
        super(team);
        pieceInfo = PieceInfo.CHA;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.isEmpty()) {
            return true;
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            if (!piecesOnPathWithTargetOrNot.values()
                    .stream()
                    .findFirst()
                    .get()) {
                return false;
            }
            return piecesOnPathWithTargetOrNot.keySet()
                    .stream()
                    .findFirst()
                    .get()
                    .getTeam() != this.team;
        }
        return false;
    }
}
