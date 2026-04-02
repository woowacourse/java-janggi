package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.side.TeamType;

import java.util.List;

public class Ma extends LeapingPiece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.UP, Delta.RIGHT_UP)),
            new MovePath(List.of(Delta.UP, Delta.LEFT_UP)),
            new MovePath(List.of(Delta.DOWN, Delta.RIGHT_DOWN)),
            new MovePath(List.of(Delta.DOWN, Delta.LEFT_DOWN)),
            new MovePath(List.of(Delta.LEFT, Delta.LEFT_UP)),
            new MovePath(List.of(Delta.LEFT, Delta.LEFT_DOWN)),
            new MovePath(List.of(Delta.RIGHT, Delta.RIGHT_UP)),
            new MovePath(List.of(Delta.RIGHT, Delta.RIGHT_DOWN))
    );

    public Ma(TeamType teamType) {
        super(teamType, PieceType.MA);
    }

    @Override
    protected List<MovePath> getPaths() {
        return PATHS;
    }
}
