package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.ByeongMovement;

import java.util.ArrayList;
import java.util.List;

public class Byeong extends Piece {
    public Byeong(Country country) {
        super(country, PieceType.BYEONG);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        Country country = janggiBoard.findCountryByCoordinate(currCoordinate);

        if (country == Country.CHO) {
            for (ByeongMovement byeongMovement : ByeongMovement.values()) {
                if (byeongMovement == ByeongMovement.DOWN) {
                    continue;
                }
                JanggiCoordinate next = movePosition(currCoordinate, byeongMovement.getDirection());
                if (!janggiBoard.isOutOfBoundary(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                    availablePositions.add(next);
                }
            }
            return availablePositions;
        }

        for (ByeongMovement byeongMovement : ByeongMovement.values()) {
            if (byeongMovement == ByeongMovement.UP) {
                continue;
            }
            JanggiCoordinate next = movePosition(currCoordinate, byeongMovement.getDirection());
            if (!janggiBoard.isOutOfBoundary(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                availablePositions.add(next);
            }
        }
        return availablePositions;
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
