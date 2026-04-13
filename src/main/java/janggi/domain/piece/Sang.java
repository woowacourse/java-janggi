package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.team.TeamType;

import java.util.List;

public class Sang extends LeapingPiece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.up(), Delta.rightUp(), Delta.rightUp())),
            new MovePath(List.of(Delta.up(), Delta.leftUp(), Delta.leftUp())),
            new MovePath(List.of(Delta.down(), Delta.rightDown(), Delta.rightDown())),
            new MovePath(List.of(Delta.down(), Delta.leftDown(), Delta.leftDown())),
            new MovePath(List.of(Delta.left(), Delta.leftUp(), Delta.leftUp())),
            new MovePath(List.of(Delta.left(), Delta.leftDown(), Delta.leftDown())),
            new MovePath(List.of(Delta.right(), Delta.rightUp(), Delta.rightUp())),
            new MovePath(List.of(Delta.right(), Delta.rightDown(), Delta.rightDown()))
    );

    public Sang(TeamType teamType) {
        super(teamType, PieceType.SANG);
    }

    @Override
    protected List<MovePath> getPaths() {
        return PATHS;
    }
}
