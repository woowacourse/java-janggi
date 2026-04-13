package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.team.TeamType;

import java.util.List;

public class Gung extends SteppingPiece {

    public Gung(TeamType teamType) {
        super(teamType, PieceType.GUNG);
    }

    @Override
    protected List<MovePath> getPaths() {
        return List.of();
    }

    @Override
    protected void validateDiagonalDirection(Delta dxDelta) {
    }
}
