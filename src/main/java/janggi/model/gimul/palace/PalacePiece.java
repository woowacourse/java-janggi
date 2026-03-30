package janggi.model.gimul.palace;

import janggi.model.Team;
import janggi.model.board.PositionPath;
import janggi.model.gimul.Piece;
import janggi.model.board.position.Position;
import janggi.model.board.movement.Movement;
import janggi.model.board.movement.OneStepMovement;
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
    public boolean canPassThrough(List<Piece> gimulsOnPath, Piece pieceAtTo) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }

    @Override
    public boolean canPassThrough(List<Piece> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }

}
