package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.side.TeamType;

import java.util.List;

public class Jol extends SteppingPiece {

    private static final List<MovePath> CHU_PATHS = List.of(
            new MovePath(List.of(Delta.up())),
            new MovePath(List.of(Delta.left())),
            new MovePath(List.of(Delta.right()))
    );

    private static final List<MovePath> HAN_PATHS = List.of(
            new MovePath(List.of(Delta.down())),
            new MovePath(List.of(Delta.left())),
            new MovePath(List.of(Delta.right()))
    );

    public Jol(TeamType teamType) {
        super(teamType, PieceType.JOL);
    }

    @Override
    protected List<MovePath> getPaths() {
        if (getTeamType() == TeamType.CHU) {
            return CHU_PATHS;
        }
        return HAN_PATHS;
    }
}
