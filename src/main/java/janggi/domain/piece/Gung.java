package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.side.TeamType;

import java.util.List;

public class Gung extends SteppingPiece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.up())),
            new MovePath(List.of(Delta.down())),
            new MovePath(List.of(Delta.right())),
            new MovePath(List.of(Delta.left())),
            new MovePath(List.of(Delta.rightUp())),
            new MovePath(List.of(Delta.rightDown())),
            new MovePath(List.of(Delta.leftUp())),
            new MovePath(List.of(Delta.leftDown()))
    );

    public Gung(TeamType teamType) {
        super(teamType, PieceType.GUNG);
    }

    @Override
    protected List<MovePath> getPaths() {
        return PATHS;
    }
}
