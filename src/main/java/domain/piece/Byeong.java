package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

public class Byeong extends Piece {
    public Byeong(Country country) {
        super(country, PieceType.BYEONG);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateCoordinate(to);
        validateByeongMove(from, to);
        validateTarget(board, from, to);
    }

    private void validateByeongMove(JanggiCoordinate from, JanggiCoordinate to) {
        if (super.getCountry() == Country.HAN) {
            validateHanMove(from, to);
            return;
        }
        validateChoMove(from, to);
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
