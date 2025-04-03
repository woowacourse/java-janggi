package janggi.domain.piece;

import janggi.domain.board.Point;
import janggi.domain.camp.Camp;
import janggi.domain.piece.type.MoveType;
import janggi.domain.piece.type.PieceType;
import java.util.Set;

public final class Guard extends Piece {

    public Guard(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMovementRule(MoveType moveType, Point from, Point to) {
        if (!isGuardMove(from, to)) {
            throw new IllegalArgumentException("사는 상하좌우, 대각선으로 한 칸만 움직일 수 있습니다.");
        }
        validateArea(from, to);
    }

    private void validateArea(Point from, Point to) {
        if (isSameCamp(Camp.CHU)) {
            validateChuArea(from, to);
            return;
        }
        validateHanArea(from, to);
    }

    private void validateChuArea(Point from, Point to) {
        Set<Point> chuGeneralArea = Set.of(
                new Point(3, 0), new Point(3, 1), new Point(3, 2),
                new Point(4, 0), new Point(4, 1), new Point(4, 2),
                new Point(5, 0), new Point(5, 1), new Point(5, 2)
        );
        if (!chuGeneralArea.contains(from) || !chuGeneralArea.contains(to)) {
            throw new IllegalArgumentException("사는 궁성 밖을 나갈 수 없습니다.");
        }
    }

    private void validateHanArea(Point from, Point to) {
        Set<Point> hanGeneralArea = Set.of(
                new Point(3, 7), new Point(3, 8), new Point(3, 9),
                new Point(4, 7), new Point(4, 8), new Point(4, 9),
                new Point(5, 7), new Point(5, 8), new Point(5, 9)
        );
        if (!hanGeneralArea.contains(from) || !hanGeneralArea.contains(to)) {
            throw new IllegalArgumentException("사는 궁성 밖을 나갈 수 없습니다.");
        }
    }

    private boolean isGuardMove(Point from, Point to) {
        int xDistance = from.xDistanceTo(to);
        int yDistance = from.yDistanceTo(to);
        return (xDistance == 1 && yDistance == 1) || (xDistance == 0 && yDistance == 1)
                || (xDistance == 1 && yDistance == 0);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GUARD;
    }
}
