package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.position.PositionPath;
import janggi.model.piece.Piece;
import janggi.model.position.Position;
import janggi.model.movement.Movement;
import janggi.model.movement.StraightMovement;
import java.util.List;

public abstract class StraightMovePiece extends Piece {

    protected final Movement movement;

    protected StraightMovePiece(Team team) {
        super(team);
        this.movement = new StraightMovement();
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }


    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }
}
