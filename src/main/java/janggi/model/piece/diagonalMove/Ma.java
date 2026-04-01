package janggi.model.piece.diagonalMove;

import janggi.model.Team;
import janggi.model.movement.MaMovement;
import janggi.model.movement.Movement;
import janggi.model.piece.PieceType;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;

public class Ma extends DiagonalMovePiece {

    private final Movement movement;

    private Ma(Team team, PieceType pieceType, Movement movement) {
        super(team, pieceType);
        this.movement = movement;
    }

    public Ma(Team team) {
        this(
                team,
                PieceType.MA,
                new MaMovement()
        );
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }
}
