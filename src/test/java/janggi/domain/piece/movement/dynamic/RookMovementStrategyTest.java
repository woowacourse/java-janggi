package janggi.domain.piece.movement.dynamic;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.pieces.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class RookMovementStrategyTest {

    private static final Side ALLY_SIDE = Side.CHO;
    private static final Side ENEMY_SIDE = Side.HAN;
    private static final RookMovementStrategy ROOK_MOVEMENT_STRATEGY = new RookMovementStrategy();

    private static Piece createAllyRook(int x, int y) {
        return new Piece(PieceType.ROOK, ROOK_MOVEMENT_STRATEGY, ALLY_SIDE, x, y);
    }

    private static Piece createEnemyRook(int x, int y) {
        return new Piece(PieceType.ROOK, ROOK_MOVEMENT_STRATEGY, ENEMY_SIDE, x, y);
    }

    private static Pieces makePieces(Piece... pieces) {
        return new Pieces(Arrays.stream(pieces).collect(Collectors.toMap(Piece::getPosition, Function.identity())));
    }

    private static Stream<Arguments> 이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                makePieces(createAllyRook(1, 5), createAllyRook(1, 7)),
                ALLY_SIDE,
                new Position(1, 2),
                new Position(1, 7)
            ),
            Arguments.of(
                makePieces(createAllyRook(4, 2), createAllyRook(6, 2)),
                ALLY_SIDE,
                new Position(1, 2),
                new Position(6, 2)
            )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(ALLY_SIDE, new Position(1, 2), new Position(1, 7)),
            Arguments.of(ALLY_SIDE, new Position(1, 2), new Position(6, 2)),
            Arguments.of(ALLY_SIDE, new Position(1, 2), new Position(5, 2))
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                makePieces(createEnemyRook(3, 2)),
                ALLY_SIDE,
                new Position(1, 2),
                new Position(3, 2)
            ),
            Arguments.of(
                makePieces(createEnemyRook(4, 2)),
                ALLY_SIDE,
                new Position(1, 2),
                new Position(4, 2)
            ),
            Arguments.of(
                makePieces(createEnemyRook(5, 2)),
                ALLY_SIDE,
                new Position(1, 2),
                new Position(5, 2)
            )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                makePieces(createAllyRook(3, 2)),
                ALLY_SIDE,
                new Position(1, 2),
                new Position(3, 2)
            ),
            Arguments.of(
                makePieces(createAllyRook(4, 2)),
                ALLY_SIDE,
                new Position(1, 2),
                new Position(4, 2)
            ),
            Arguments.of(
                makePieces(createAllyRook(5, 2)),
                ALLY_SIDE,
                new Position(1, 2),
                new Position(5, 2)
            )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_x_y좌표가_현재_x_y좌표와_모두_다르면_움직일_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(ALLY_SIDE, new Position(1, 2), new Position(2, 3)),
            Arguments.of(ALLY_SIDE, new Position(1, 2), new Position(3, 4)),
            Arguments.of(ALLY_SIDE, new Position(1, 2), new Position(4, 5))
        );
    }

    private static Stream<Arguments> 이동하고자_하는_x_y좌표가_현재_x_y좌표와_하나만_다르면_움직일_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(ALLY_SIDE, new Position(1, 2), new Position(3, 2)),
            Arguments.of(ALLY_SIDE, new Position(1, 2), new Position(1, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_x_y좌표가_현재_x_y좌표와_모두_다르면_움직일_수_없다_테스트_케이스")
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_모두_다르면_움직일_수_없다(Side side, Position origin, Position destination) {
        assertThat(ROOK_MOVEMENT_STRATEGY.isLegalDestination(side, origin, destination))
            .isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_x_y좌표가_현재_x_y좌표와_하나만_다르면_움직일_수_있다_테스트_케이스")
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_하나만_다르면_움직일_수_있다(Side side, Position origin, Position destination) {
        assertThat(ROOK_MOVEMENT_STRATEGY.isLegalDestination(side, origin, destination))
            .isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다(Pieces map, Side side, Position origin, Position destination) {
        assertThat(ROOK_MOVEMENT_STRATEGY.isLegalPath(map, side, origin, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다(Side side, Position origin, Position destination) {
        assertThat(ROOK_MOVEMENT_STRATEGY.isLegalPath(makePieces(), side, origin, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다(Pieces map, Side side, Position origin, Position destination) {
        assertThat(ROOK_MOVEMENT_STRATEGY.isLegalPath(map, side, origin, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다(Pieces map, Side side, Position origin, Position destination) {
        assertThat(ROOK_MOVEMENT_STRATEGY.isLegalPath(map, side, origin, destination)).isFalse();
    }
}
