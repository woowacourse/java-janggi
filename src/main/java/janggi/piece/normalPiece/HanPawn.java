package janggi.piece.normalPiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.route.Routes;

public class HanPawn extends NormalPiece {

    public HanPawn(Position position) {
        super(Team.HAN, position, Routes.ofHanPawn());
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

    @Override
    public Piece move(Team team, Position destination) {
        return new HanPawn(destination);
    }
}
