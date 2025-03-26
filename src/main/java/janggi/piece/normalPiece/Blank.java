package janggi.piece.normalPiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.route.Routes;

public class Blank extends NormalPiece {

    public Blank(Position position) {
        super(Team.BLANK, position, Routes.ofBlank());
    }

    @Override
    public PieceType type() {
        return PieceType.BLANK;
    }

    @Override
    public Piece move(Team team, Position destination) {
        return new Blank(destination);
    }
}
