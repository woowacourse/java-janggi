package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.DiagonalStraightGenerator;
import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.OrthogonalStraightPathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.ConditionalMovementStrategy;
import domain.strategy.MovementStrategy;

public class Cha extends Piece {
    private static final MovementStrategy MOVEMENT_STRATEGY =
            new ConditionalMovementStrategy(
                    new BlockedMovementStrategy(),
                    pathPieces -> pathPieces.isOrthogonalMove() || pathPieces.isPalaceMove()
            );
    private static final PathGenerator STRAIGHT_PATH_GENERATOR = new OrthogonalStraightPathGenerator();
    private static final PathGenerator DIAGONAL_PATH_GENERATOR = new DiagonalStraightGenerator();

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        if (source.row() == destination.row() || source.column() == destination.column()) {
            return STRAIGHT_PATH_GENERATOR.calculatePath(source, destination);
        }
        return DIAGONAL_PATH_GENERATOR.calculatePath(source, destination);
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return MOVEMENT_STRATEGY.validatePath(pathPieces);
    }
}
