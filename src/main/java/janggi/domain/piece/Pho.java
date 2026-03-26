package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;
import java.util.List;

public class Pho extends Piece {

    public Pho(Team team) {
        super(team, PieceType.PHO);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (moveStrategy(from, to)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 포가 이동할 수 없습니다.");
    }

    @Override
    public void validateArrival(Space space) {
        super.validateArrival(space);

        Piece piece = (Piece) space;
        if (isSameType(piece)) {
            throw new IllegalArgumentException("이동하려는 위치에 상대팀의 포가 존재합니다.");
        }
    }

    @Override
    public void validateRoutes(List<Piece> pieces) {
        if (pieces.size() != 1) {
            throw new IllegalArgumentException("포는 이동 경로 사이에 포를 제외한 하나의 말이 있어야 합니다.");
        }

        if (isContainsPho(pieces)) {
            throw new IllegalArgumentException("포는 이동 경로 사이에 포를 제외한 하나의 말이 있어야 합니다.");
        }
    }

    private boolean isContainsPho(List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSameType(this));
    }

    @Override
    public List<Position> getRoutes(Position from, Position to) {
        return List.of();
    }

    private boolean moveStrategy(Position from, Position to) {
        int dx = from.diffX(to);
        int dy = from.diffY(to);

        return (Math.abs(dx) == 0 && Math.abs(dy) > 1) ||
                (Math.abs(dx) > 1 && Math.abs(dy) == 0);
    }
}
