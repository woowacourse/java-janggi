package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Po extends SlidingPiece {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Delta.UP)),
            new MovePath(List.of(Delta.DOWN)),
            new MovePath(List.of(Delta.LEFT)),
            new MovePath(List.of(Delta.RIGHT))
    );

    public Po(TeamType teamType) {
        super(teamType, PieceType.PO);
    }

    @Override
    protected List<MovePath> getPaths() {
        return PATHS;
    }

    @Override
    protected void validatePieceInPath(MovePath movePath, Position start, Position end, Board board) {
        List<Position> intermediatePositions = movePath.intermediatePositions(start, end);
        List<Piece> obstacles = intermediatePositions.stream()
                .map(board::findPiece)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        if (obstacles.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하지 않아 이동할 수 없습니다.");
        }
        if (obstacles.size() > 1) {
            throw new IllegalArgumentException("이동 경로에 기물이 2개 이상 존재합니다.");
        }
        if (obstacles.getFirst().getPieceType() == PieceType.PO) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }
}
