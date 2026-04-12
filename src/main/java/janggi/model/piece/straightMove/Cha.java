package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import java.util.List;

public class Cha extends StraightMovePiece {

    private Cha(
            Team team,
            PieceType pieceType
    ) {
        super(team, pieceType);
    }

    public Cha(Team team) {
        this(team, PieceType.CHA);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo) {
        return piecesOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }
}
