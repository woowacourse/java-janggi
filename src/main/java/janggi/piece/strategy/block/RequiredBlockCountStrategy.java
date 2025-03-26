package janggi.piece.strategy.block;

import janggi.Board;
import janggi.coordinate.Position;
import janggi.coordinate.Route;

public class RequiredBlockCountStrategy {

    private final int requireBlockCount;

    public RequiredBlockCountStrategy(final int requireBlockCount) {
        validateNegativeValue(requireBlockCount);
        this.requireBlockCount = requireBlockCount;
    }

    private static void validateNegativeValue(final int requireBlockCount) {
        if (requireBlockCount < 0) {
            throw new IllegalArgumentException("요구되는 블록 수는 음수가 될 수 없습니다.");
        }
    }

    public static RequiredBlockCountStrategy common() {
        return new RequiredBlockCountStrategy(0);
    }

    public void validate(final Board board, final Position departure, final Position destination) {
        if (countPieceInRoute(board, departure, destination) != requireBlockCount) {
            throw new IllegalArgumentException("이동 경로에 기물 갯수가 조건에 맞지 않습니다.");
        }
    }

    private int countPieceInRoute(Board board, Position departure, Position destination) {
        return (int) Route.of(departure, destination).calculate().stream()
                .filter(board::isExists)
                .count();
    }
}
