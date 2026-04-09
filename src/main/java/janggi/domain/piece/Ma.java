package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.team.TeamType;

import java.util.List;

public class Ma extends LeapingPiece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.up(), Delta.rightUp())),
            new MovePath(List.of(Delta.up(), Delta.leftUp())),
            new MovePath(List.of(Delta.down(), Delta.rightDown())),
            new MovePath(List.of(Delta.down(), Delta.leftDown())),
            new MovePath(List.of(Delta.left(), Delta.leftUp())),
            new MovePath(List.of(Delta.left(), Delta.leftDown())),
            new MovePath(List.of(Delta.right(), Delta.rightUp())),
            new MovePath(List.of(Delta.right(), Delta.rightDown()))
    );

    public Ma(TeamType teamType) {
        super(teamType, PieceType.MA);
    }

    @Override
    protected List<MovePath> getPaths() {
        return PATHS;
    }
}
