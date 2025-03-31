package janggi.domain.piece.movement.palace;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceMaker;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.normal.dynamic.PawnMovementStrategy;
import janggi.domain.piece.pieces.Pieces;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class PawnPalaceMovementStrategyTest {

    private final PawnPalaceMovementStrategy pawnPalaceMovementStrategy = new PawnPalaceMovementStrategy(
        new PawnMovementStrategy());

    private static Stream<Arguments> 한나라는_궁_안에서도_뒤로_갈_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(new Position(3, 7), new Position(3, 6)),
            Arguments.of(new Position(3, 8), new Position(3, 7)),
            Arguments.of(new Position(4, 7), new Position(4, 6)),
            Arguments.of(new Position(4, 8), new Position(4, 7))
        );
    }

    private static Stream<Arguments> 초나라는_궁_안에서도_뒤로_갈_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(new Position(3, 1), new Position(3, 2)),
            Arguments.of(new Position(3, 0), new Position(3, 1)),
            Arguments.of(new Position(4, 1), new Position(4, 2)),
            Arguments.of(new Position(4, 0), new Position(4, 1))
        );
    }

    @Test
    void 궁_안에서는_대각선으로_한칸_이동할_수_있다() {
        Piece piece = PieceMaker.createPiece(PieceType.PAWN, Side.HAN, new Position(3, 7));

        assertThat(
            pawnPalaceMovementStrategy.isMovableInPalace(new Pieces(Map.of()), piece.getPosition(), piece.getSide(),
                new Position(4, 8)))
            .isTrue();
    }

    @ParameterizedTest
    @MethodSource("한나라는_궁_안에서도_뒤로_갈_수_없다_테스트_케이스")
    void 한나라는_궁_안에서도_뒤로_갈_수_없다(Position origin, Position destination) {
        Piece piece = PieceMaker.createPiece(PieceType.PAWN, Side.HAN, origin);

        assertThat(
            pawnPalaceMovementStrategy.isMovableInPalace(new Pieces(Map.of()), piece.getPosition(), piece.getSide(),
                destination))
            .isFalse();
    }

    @ParameterizedTest
    @MethodSource("초나라는_궁_안에서도_뒤로_갈_수_없다_테스트_케이스")
    void 초나라는_궁_안에서도_뒤로_갈_수_없다(Position origin, Position destination) {
        Piece piece = PieceMaker.createPiece(PieceType.PAWN, Side.CHO, origin);

        assertThat(
            pawnPalaceMovementStrategy.isMovableInPalace(new Pieces(Map.of()), piece.getPosition(), piece.getSide(),
                destination))
            .isFalse();
    }
}
