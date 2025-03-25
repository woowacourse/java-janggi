package janggi.piece.jumpingPiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.route.Routes;

public class Cannon extends JumpingPiece {

    public Cannon(Team team, Position position) {
        super(team, position, Routes.ofCannon());
    }

    @Override
    public PieceType type() {
        return PieceType.CANNON;
    }

    @Override
    public Piece move(Team team, Position destination) {
        return new Cannon(team, destination);
    }
}
