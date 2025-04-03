package domain.piece;

import domain.*;
import domain.piece.move.CastleDiagonalMove;
import domain.piece.move.LinearMove;

public class Pho extends Piece implements LinearMove, CastleDiagonalMove {

    public Pho(Country country) {
        super(country, PieceType.PHO);
    }

    @Override
    public void validatePieceMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateLinearMove(board, from, to);
    }

    @Override
    public void validateLinearMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        if (!from.isSameRow(to) && !from.isSameCol(to)) {
            validateCastleDiagonalMove(janggiBoard, from, to);
            validateDiagonalReachable(janggiBoard, from, to);
            return;
        }

        validateRowCol(from, to);
        validateReachable(janggiBoard, from, to);
        validatePhoTarget(janggiBoard, to);
    }

    @Override
    public void validateReachable(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        Direction direction = Direction.getDirection(from, to);
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
            throw new IllegalArgumentException("[ERROR] 포는 기물을 한번 넘어 공격할 수 있습니다.");
        }
    }

    @Override
    public void validateCastleDiagonalMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        if (!board.isCastleCoordinate(from) || !board.isCastleCoordinate(to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없습니다,");
        }
        if (from.distanceTo(to) != 8) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateDiagonalReachable(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        Direction direction = Direction.getDiagonalDirection(from, to);
        JanggiCoordinate curr = from.move(direction);

        curr = findFirstPieceCoordinate(board, curr, to, direction);
        Piece currPiece = board.findPieceByCoordinate(curr);

        if (currPiece.getPieceType() == PieceType.PHO) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 넘을 수 없습니다.");
        }
        if (curr.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 넘어야 공격할 수 있습니다.");
        }

        curr = findFirstPieceCoordinate(board, curr.move(direction), to, direction);
        if (!curr.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 한번 넘어 공격할 수 있습니다.");
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
