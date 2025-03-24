package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

public class Byeong extends Piece {
    public Byeong(Country country) {
        super(country, PieceType.BYEONG);
    }

    private void validateByeongMove(JanggiCoordinate from, JanggiCoordinate to) {
        if (super.getCountry() == Country.HAN) {
            validateHanMove(from, to);
            return;
        }
        validateChoMove(from, to);
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateByeongMove(from, to);
        validateTarget(board, to);
    }

    private void validateHanMove(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.moveDown().equals(to) || from.moveRight().equals(to) || from.moveLeft().equals(to)) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치로 기물을 이동할 수 없습니다.");
    }

    private void validateChoMove(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.moveUp().equals(to) || from.moveRight().equals(to) || from.moveLeft().equals(to)) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치로 기물을 이동할 수 없습니다.");
    }
}
