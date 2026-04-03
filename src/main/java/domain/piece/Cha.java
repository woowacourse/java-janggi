package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;
import domain.strategy.PalaceMoveConstraintStrategy;

public class Cha extends Piece {
    private static final MovementStrategy MOVEMENT_STRATEGY = new PalaceMoveConstraintStrategy(new BlockedMovementStrategy());
    private static final PathGenerator PATH_GENERATOR = new StraightPathGenerator();

    public Cha(Team team) {
        super(team, PieceType.CHA);
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
