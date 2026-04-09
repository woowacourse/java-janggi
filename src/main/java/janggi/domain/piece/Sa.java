package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.team.TeamType;

import java.util.List;

public class Sa extends SteppingPiece {

    public Sa(TeamType teamType) {
        super(teamType, PieceType.SA);
    }

    @Override
    protected List<MovePath> getPaths() {
        return List.of();
    }

    @Override
    protected void validateDiagonalDirection(Delta dxDelta) {
    }
}
