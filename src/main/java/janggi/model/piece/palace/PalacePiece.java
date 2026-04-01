package janggi.model.piece.palace;

import janggi.model.Team;
import janggi.model.position.PositionPath;
import janggi.model.piece.Piece;
import janggi.model.position.Position;
import janggi.model.movement.Movement;
import janggi.model.movement.OneStepMovement;
import java.util.List;

public abstract class PalacePiece extends Piece {

    private final Movement movement;

    protected PalacePiece(Team team) {
        super(team);
        movement = new OneStepMovement();
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo) {
        return piecesOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }

}
