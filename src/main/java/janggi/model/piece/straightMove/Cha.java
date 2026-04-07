package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.palace.Palaces;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import java.util.List;

public class Cha extends StraightMovePiece {

    private Cha(
            Team team,
            PieceType pieceType,
            Palaces palaces
    ) {
        super(team, pieceType, palaces);
    }

    public Cha(Team team, Palaces palaces) {
        this(team, PieceType.CHA, palaces);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo) {
        return piecesOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }
}
