package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;
import domain.piece.move.CastlePieceMove;
import domain.piece.move.DistanceMove;

import java.util.function.Predicate;

public class Gung extends Piece implements DistanceMove, CastlePieceMove {
    private static final int GUNG_REACHABLE_DISTANCE = 2;
    private static final Predicate<Integer> isReachAble = (dist) -> dist <= GUNG_REACHABLE_DISTANCE;

    public Gung(Country country) {
        super(country, PieceType.GUNG);
    }

    @Override
    public void validatePieceMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateCastleCoordinate(board, to);
        validateReachableDistanceCoordinate(from, to, isReachAble);
        validateCastlePieceMove(board, from, to);
    }
}
