package janggi.piece.normalPiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.route.Routes;

public class Palace extends NormalPiece {

    public Palace(Team team, Position position) {
        super(team, position, Routes.ofPalace());
    }

    @Override
    public PieceType type() {
        return PieceType.PALACE;
    }

    @Override
    public Piece move(Team team, Position destination) {
        return new Palace(team, destination);
    }
}
