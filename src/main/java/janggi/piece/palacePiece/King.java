package janggi.piece.palacePiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.route.Routes;

public class King extends PalacePiece {

    public King(Team team, Position position) {
        super(team, position, Routes.ofPalace());
    }

    @Override
    public PieceType type() {
        return PieceType.KING;
    }

    @Override
    public Piece move(Team team, Position destination) {
        return new King(team, destination);
    }
}
