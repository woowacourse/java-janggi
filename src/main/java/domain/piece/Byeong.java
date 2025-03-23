package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.List;

public class Byeong extends Piece {

    private final List<Movement> movements = List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT);

    public Byeong(Country country) {
        super(country, PieceType.BYEONG);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate, Board board) {
        Country country = board.findCountryByCoordinate(currCoordinate);
        return movements.stream()
                .filter(movement -> {
                    if (country == Country.CHO) {
                        return movement != Movement.DOWN;
                    }
                    return movement != Movement.UP;
                })
                .map(Movement -> movePosition(currCoordinate, Movement.getDirection()))
                .filter(next -> !board.isOutOfBoundary(next) && !board.isMyTeam(currCoordinate, next))
                .toList();
    }

    public static Coordinate movePosition(Coordinate currCoordinate, Coordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
