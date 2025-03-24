package domain.piece;

import domain.*;

public class Cha extends Piece implements LinearMove {

    public Cha(Country country) {
        super(country, PieceType.CHA);
    }

    @Override
    public void validateLinearMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        validateRowCol(from, to);
        validateReachAble(janggiBoard, from, to);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateLinearMove(board, from, to);
        validateTarget(board, from, to);
    }

    @Override
    public void validateReachAble(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        Direction direction = getDirection(from, to);
        JanggiCoordinate curr = from.move(direction);
        while (!janggiBoard.isOccupied(curr) && !curr.equals(to)) {
            curr = curr.move(direction);
        }
        if (janggiBoard.isOccupied(curr) &&
                janggiBoard.findPieceByCoordinate(curr) != janggiBoard.findPieceByCoordinate(to)) {
            throw new IllegalArgumentException("[ERROR] 기물을 넘어서 이동 할 수 없습니다.");
        }
    }
}
