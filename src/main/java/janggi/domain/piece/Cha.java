package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.team.TeamType;

import java.util.List;

public class Cha extends SlidingPiece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.up())),
            new MovePath(List.of(Delta.down())),
            new MovePath(List.of(Delta.left())),
            new MovePath(List.of(Delta.right()))
    );

    public Cha(TeamType teamType) {
        super(teamType, PieceType.CHA);
    }


    @Override
    protected List<MovePath> getPaths() {
        return PATHS;
    }

    @Override
    public void validateCanMove(List<Piece> piecesInPath) {
        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }
}
