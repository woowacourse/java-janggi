package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.MovementStrategy;
import domain.strategy.PalaceMoveConstraintStrategy;
import domain.strategy.PoMovementStrategy;

public class Po extends Piece {
    private static final MovementStrategy MOVEMENT_STRATEGY = new PalaceMoveConstraintStrategy(new PoMovementStrategy());
    private static final PathGenerator PATH_GENERATOR = new StraightPathGenerator();

    public Po(Team team) {
        super(team, PieceType.PO);
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        return PATH_GENERATOR.calculatePath(source, destination);
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return MOVEMENT_STRATEGY.validatePath(pathPieces);
    }
}
