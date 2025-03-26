package domain.piece;

import domain.*;

public class Pho extends Piece implements LinearMove {

    public Pho(Country country) {
        super(country, PieceType.PHO);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateCoordinate(to);
        validateLinearMove(board, from, to);
        validateTarget(board, from, to);
    }

    @Override
    public void validateLinearMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        validateRowCol(from, to);
        validateReachAble(janggiBoard, from, to);
        validatePhoTarget(janggiBoard, to);
    }

    @Override
    public void validateReachAble(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        Direction direction = getDirection(from, to);
        JanggiCoordinate curr = from.move(direction);

        curr = findFirstPieceCoordinate(janggiBoard, curr, to, direction);
        Piece currPiece = janggiBoard.findPieceByCoordinate(curr);

        if (currPiece.getPieceType() == PieceType.PHO) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 넘을 수 없습니다.");
        }
        if (curr.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 넘어야 공격할 수 있습니다.");
        }

        curr = findFirstPieceCoordinate(janggiBoard, curr.move(direction), to, direction);
        if (!curr.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 한번만 넘어 공격할 수 있습니다.");
        }
    }

    private JanggiCoordinate findFirstPieceCoordinate(JanggiBoard janggiBoard, JanggiCoordinate curr, JanggiCoordinate to, Direction direction) {
        while (!janggiBoard.isOccupied(curr) && !curr.equals(to)) {
            curr = curr.move(direction);
        }
        return curr;
    }

    private void validatePhoTarget(JanggiBoard board, JanggiCoordinate to) {
        if (!board.isOccupied(to)) {
            return;
        }

        Piece targetPiece = board.findPieceByCoordinate(to);
        if (targetPiece.getPieceType() == PieceType.PHO) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }
}
