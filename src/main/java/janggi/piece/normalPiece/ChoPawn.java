package janggi.piece.normalPiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.route.Routes;

public class ChoPawn extends NormalPiece {

    public ChoPawn(Position position) {
        super(Team.CHO, position, Routes.ofChoPawn());
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

    @Override
    public Piece move(Team team, Position destination) {
        return new ChoPawn(destination);
    }
}
