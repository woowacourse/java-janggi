package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import java.util.List;

public class Pho extends StraightMovePiece {
    private Pho(
            Team team,
            PieceType pieceType
    ) {
        super(team, pieceType);
    }

    public Pho(Team team) {
        this(team, PieceType.PHO);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo) {
        return piecesOnPath.size() == 1
                && !(piecesOnPath.getFirst().getPieceType() == PieceType.PHO)
                && !this.isSameTeam(pieceAtTo);
    }
}
