package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.DiagonalStraightGenerator;
import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.MovementStrategy;
import domain.strategy.PoMovementStrategy;

public class Po extends Piece {
    private static final MovementStrategy MOVEMENT_STRATEGY = new PoMovementStrategy();
    private static final PathGenerator STRAIGHT_PATH_GENERATOR = new StraightPathGenerator();
    private static final PathGenerator DIAGONAL_PATH_GENERATOR = new DiagonalStraightGenerator();

    public Po(Team team) {
        super(team, PieceType.PO);
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
