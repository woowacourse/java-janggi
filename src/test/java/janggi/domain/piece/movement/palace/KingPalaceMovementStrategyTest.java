package janggi.domain.piece.movement.palace;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceMaker;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.normal.fixed.KingMovementStrategy;
import janggi.domain.piece.pieces.Pieces;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class KingPalaceMovementStrategyTest {

    private static final KingPalaceMovementStrategy KING_PALACE_MOVEMENT_STRATEGY =
        new KingPalaceMovementStrategy(new KingMovementStrategy());

    private static Pieces makePieces(Piece... pieces) {
        return new Pieces(Arrays.stream(pieces).collect(Collectors.toMap(Piece::getPosition, Function.identity())));
    }

    private static Stream<Arguments> 킹은_궁성_내에서_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(new Position(3, 0), Side.HAN, new Position(3, 1), true),
            Arguments.of(new Position(3, 0), Side.HAN, new Position(4, 0), true),
            Arguments.of(new Position(3, 0), Side.HAN, new Position(4, 1), true),

            Arguments.of(new Position(4, 0), Side.HAN, new Position(3, 0), true),
            Arguments.of(new Position(4, 0), Side.HAN, new Position(4, 1), true),
            Arguments.of(new Position(4, 0), Side.HAN, new Position(5, 0), true),

            Arguments.of(new Position(5, 0), Side.HAN, new Position(4, 0), true),
            Arguments.of(new Position(5, 0), Side.HAN, new Position(4, 1), true),
            Arguments.of(new Position(5, 0), Side.HAN, new Position(5, 1), true),

            Arguments.of(new Position(3, 1), Side.HAN, new Position(3, 0), true),
            Arguments.of(new Position(3, 1), Side.HAN, new Position(3, 2), true),
            Arguments.of(new Position(3, 1), Side.HAN, new Position(4, 1), true),

            Arguments.of(new Position(4, 1), Side.HAN, new Position(3, 2), true),
            Arguments.of(new Position(4, 1), Side.HAN, new Position(4, 2), true),
            Arguments.of(new Position(4, 1), Side.HAN, new Position(5, 2), true),
            Arguments.of(new Position(4, 1), Side.HAN, new Position(3, 1), true),
            Arguments.of(new Position(4, 1), Side.HAN, new Position(5, 1), true),
            Arguments.of(new Position(4, 1), Side.HAN, new Position(3, 0), true),
            Arguments.of(new Position(4, 1), Side.HAN, new Position(4, 0), true),
            Arguments.of(new Position(4, 1), Side.HAN, new Position(5, 0), true),

            Arguments.of(new Position(3, 7), Side.HAN, new Position(3, 8), true),
            Arguments.of(new Position(3, 7), Side.HAN, new Position(4, 7), true),
            Arguments.of(new Position(3, 7), Side.HAN, new Position(4, 8), true),

            Arguments.of(new Position(4, 7), Side.HAN, new Position(3, 7), true),
            Arguments.of(new Position(4, 7), Side.HAN, new Position(4, 8), true),
            Arguments.of(new Position(4, 7), Side.HAN, new Position(5, 7), true),

            Arguments.of(new Position(5, 7), Side.HAN, new Position(4, 7), true),
            Arguments.of(new Position(5, 7), Side.HAN, new Position(4, 8), true),
            Arguments.of(new Position(5, 7), Side.HAN, new Position(5, 8), true),

            Arguments.of(new Position(3, 8), Side.HAN, new Position(3, 7), true),
            Arguments.of(new Position(3, 8), Side.HAN, new Position(3, 9), true),
            Arguments.of(new Position(3, 8), Side.HAN, new Position(4, 8), true),

            Arguments.of(new Position(4, 8), Side.HAN, new Position(3, 9), true),
            Arguments.of(new Position(4, 8), Side.HAN, new Position(4, 9), true),
            Arguments.of(new Position(4, 8), Side.HAN, new Position(5, 9), true),
            Arguments.of(new Position(4, 8), Side.HAN, new Position(3, 8), true),
            Arguments.of(new Position(4, 8), Side.HAN, new Position(5, 8), true),
            Arguments.of(new Position(4, 8), Side.HAN, new Position(3, 7), true),
            Arguments.of(new Position(4, 8), Side.HAN, new Position(4, 7), true),
            Arguments.of(new Position(4, 8), Side.HAN, new Position(5, 7), true),

            Arguments.of(new Position(3, 7), Side.HAN, new Position(3, 9), false),
            Arguments.of(new Position(3, 7), Side.HAN, new Position(5, 7), false),
            Arguments.of(new Position(3, 7), Side.HAN, new Position(5, 9), false),

            Arguments.of(new Position(3, 8), Side.HAN, new Position(4, 7), false),
            Arguments.of(new Position(3, 8), Side.HAN, new Position(4, 9), false)
        );
    }

    @ParameterizedTest
    @MethodSource("킹은_궁성_내에서_이동할_수_있다_테스트_케이스")
    void 킹은_궁성_내에서_이동할_수_있다(Position origin, Side side, Position destination, boolean expected) {
        Pieces map = makePieces();

        assertThat(KING_PALACE_MOVEMENT_STRATEGY.isMovableInPalace(map, origin, side, destination))
            .isEqualTo(expected);
    }

    @Test
    void 목적지에_적군이_있다면_움직일_수_있다() {
        Pieces map = makePieces(PieceMaker.createPiece(PieceType.ROOK, Side.HAN, new Position(5, 0)));
        Side side = Side.CHO;
        Position origin = new Position(4, 1);
        Position destination = new Position(5, 0);

        assertThat(KING_PALACE_MOVEMENT_STRATEGY.isMovableInPalace(map, origin, side, destination))
            .isTrue();
    }

    @Test
    void 목적지에_아군이_있다면_움직일_수_없다() {
        Pieces map = makePieces(PieceMaker.createPiece(PieceType.ROOK, Side.CHO, new Position(5, 0)));
        Side side = Side.CHO;
        Position origin = new Position(4, 1);
        Position destination = new Position(5, 0);

        assertThat(KING_PALACE_MOVEMENT_STRATEGY.isMovableInPalace(map, origin, side, destination))
            .isFalse();
    }
}
