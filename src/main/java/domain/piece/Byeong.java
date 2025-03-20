package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.ByeongMovement;

import java.util.Arrays;
import java.util.List;

public class Byeong extends Piece {
    public Byeong(Country country) {
        super(country, PieceType.BYEONG);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        Country country = janggiBoard.findCountryByCoordinate(currCoordinate);
        return Arrays.stream(ByeongMovement.values())
                .filter(byeongMovement -> {
                    if (country == Country.CHO) {
                        return byeongMovement != ByeongMovement.DOWN;
                    }
                    return byeongMovement != ByeongMovement.UP;
                })
                .map(byeongMovement -> movePosition(currCoordinate, byeongMovement.getDirection()))
                .filter(next -> !janggiBoard.isOutOfBoundary(next) && !janggiBoard.isMyTeam(currCoordinate, next))
                .toList();
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
