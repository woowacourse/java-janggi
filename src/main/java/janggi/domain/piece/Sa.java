package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.side.TeamType;

import java.util.List;

public class Sa extends SteppingPiece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.UP)),
            new MovePath(List.of(Delta.DOWN)),
            new MovePath(List.of(Delta.LEFT)),
            new MovePath(List.of(Delta.RIGHT)),
            new MovePath(List.of(Delta.RIGHT_UP)),
            new MovePath(List.of(Delta.RIGHT_DOWN)),
            new MovePath(List.of(Delta.LEFT_UP)),
            new MovePath(List.of(Delta.LEFT_DOWN))
    );

    public Sa(TeamType teamType) {
        super(teamType, PieceType.SA);
    }

    @Override
    protected List<MovePath> getPaths() {
        return PATHS;
    }
}
