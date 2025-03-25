package janggi.piece.normalPiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.route.Routes;

public class Horse extends NormalPiece {

    public Horse(Team team, Position position) {
        super(team, position, Routes.ofHorse());
    }

    @Override
    public PieceType type() {
        return PieceType.HORSE;
    }

    @Override
    public Piece move(Team team, Position destination) {
        return new Horse(team, destination);
    }
}
