package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.ByeongMovement;
import java.util.Arrays;
import java.util.List;

public class Byeong extends Piece {
    public Byeong(Country country) {
        super(country, PieceType.BYEONG);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate,
                                                   Board board) {
        Country country = board.findCountryByCoordinate(currCoordinate);
        return Arrays.stream(ByeongMovement.values())
                .filter(byeongMovement -> {
                    if (country == Country.CHO) {
                        return byeongMovement != ByeongMovement.DOWN;
                    }
                    return byeongMovement != ByeongMovement.UP;
                })
                .map(byeongMovement -> movePosition(currCoordinate, byeongMovement.getDirection()))
                .filter(next -> !board.isOutOfBoundary(next) && !board.isMyTeam(currCoordinate, next))
                .toList();
    }

    public static Coordinate movePosition(Coordinate currCoordinate, Coordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
