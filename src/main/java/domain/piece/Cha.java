package domain.piece;

import domain.*;
import domain.piece.move.CastleDiagonalMove;
import domain.piece.move.LinearMove;

public class Cha extends Piece implements LinearMove, CastleDiagonalMove {

    public Cha(Country country) {
        super(country, PieceType.CHA);
    }

    @Override
    public void validatePieceMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        if (!from.isSameRow(to) && !from.isSameCol(to)) {
            validateCastleDiagonalMove(board, from, to);
            validateDiagonalReachable(board, from, to);
            return;
        }
        validateLinearMove(board, from, to);
    }

    @Override
    public void validateLinearMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        validateRowCol(from, to);
        validateReachable(janggiBoard, from, to);
    }

    @Override
    public void validateReachable(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        Direction direction = Direction.getDirection(from, to);
        JanggiCoordinate curr = from.move(direction);
        while (!janggiBoard.isOccupied(curr) && !curr.equals(to)) {
            curr = curr.move(direction);
        }
        if (janggiBoard.isOccupied(curr) &&
                janggiBoard.findPieceByCoordinate(curr) != janggiBoard.findPieceByCoordinate(to)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 있어 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateCastleDiagonalMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        if (!board.isCastleCoordinate(from) || !board.isCastleCoordinate(to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없습니다,");
        }
        if (from.distanceTo(to) != 2 && from.distanceTo(to) != 8) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateDiagonalReachable(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        Direction diagonalDirection = Direction.getDiagonalDirection(from, to);
        if (!board.castleCoordinateHasDirection(from, diagonalDirection)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없습니다.");
        }

        JanggiCoordinate curr = from.move(diagonalDirection);
        while (!board.isOccupied(curr) && !curr.equals(to)) {
            curr = curr.move(diagonalDirection);
        }
        if (board.isOccupied(curr) &&
                board.findPieceByCoordinate(curr) != board.findPieceByCoordinate(to)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 있어 해당 위치로 이동할 수 없습니다.");
        }
    }
}
