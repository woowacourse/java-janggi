package janggi.piece;

import janggi.board.point.Point;
import janggi.board.Board;

public final class Soldier extends Piece {

    public Soldier(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (isPlacedAtBottom()) {
            validateJolMove(fromPoint, toPoint);
            return;
        }
        validateByeongMove(fromPoint, toPoint);
    }

    private void validateJolMove(Point fromPoint, Point toPoint) {
        if (toPoint.y() < fromPoint.y()) {
            throw new IllegalArgumentException("졸은 뒤로 갈 수 없습니다.");
        }
        if (Math.abs(toPoint.y() - fromPoint.y() + fromPoint.x() - toPoint.x()) != 1) {
            throw new IllegalArgumentException("졸은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    private void validateByeongMove(Point fromPoint, Point toPoint) {
        if (fromPoint.y() < toPoint.y()) {
            throw new IllegalArgumentException("병은 뒤로 갈 수 없습니다.");
        }
        if (Math.abs(toPoint.y() - fromPoint.y() + fromPoint.x() - toPoint.x()) != 1) {
            throw new IllegalArgumentException("병은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        if (isPlacedAtBottom()) {
            return PieceSymbol.SOLDIER_JOL;
        }
        return PieceSymbol.SOLDIER_BYEONG;
    }
}
