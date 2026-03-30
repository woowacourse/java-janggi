package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.MovementStrategy;

public class None extends Piece {

    public None() {
        super(null, PieceType.NONE);
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        return null;
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return false;
    }

    @Override
    public boolean isDifferentTeam(Piece piece) {
        return false;
    }
}
