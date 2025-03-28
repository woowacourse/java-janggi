package janggi.piece.palacePiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.route.Routes;

public class Soldier extends PalacePiece {

    public Soldier(Team team, Position position) {
        super(team, position, Routes.ofSoldier());
    }

    @Override
    public PieceType type() {
        return PieceType.SOLDIER;
    }

    @Override
    public Piece move(Team team, Position destination) {
        return new Soldier(team, destination);
    }
}
