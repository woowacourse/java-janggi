package janggi.domain.piece;

import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.team.TeamType;

import java.util.List;

public class Po extends SlidingPiece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.up())),
            new MovePath(List.of(Delta.down())),
            new MovePath(List.of(Delta.left())),
            new MovePath(List.of(Delta.right()))
    );

    public Po(TeamType teamType) {
        super(teamType, PieceType.PO);
    }

    @Override
    protected List<MovePath> getPaths() {
        return PATHS;
    }

    @Override
    public void validateCanMove(List<Piece> piecesInPath) {
        if (piecesInPath.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하지 않아 이동할 수 없습니다.");
        }
        if (piecesInPath.size() > 1) {
            throw new IllegalArgumentException("이동 경로에 기물이 2개 이상 존재합니다.");
        }
        if (piecesInPath.getFirst().getPieceType() == PieceType.PO) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }
}
