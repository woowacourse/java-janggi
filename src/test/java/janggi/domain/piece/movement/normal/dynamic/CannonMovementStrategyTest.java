package janggi.domain.piece.movement.normal.dynamic;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceMaker;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.pieces.Pieces;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class CannonMovementStrategyTest {

    private static final Side ALLY_SIDE = Side.CHO;
    private static final Side ENEMY_SIDE = Side.HAN;
    private static final CannonMovementStrategy CANNON_MOVEMENT_STRATEGY = new CannonMovementStrategy();


    private static Pieces createPieces(Piece... pieces) {
        return Pieces.from(List.of(pieces));
    }

    private static Stream<Arguments> 이동하고자_하는_위치_내에_두_가지_이상의_기물이_있다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(
                    PieceMaker.createPiece(PieceType.ROOK, ALLY_SIDE, new Position(5, 7)),
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(5, 8))
                ),
                ALLY_SIDE,
                new Position(5, 5),
                new Position(5, 9)
            ),
            Arguments.of(
                createPieces(
                    PieceMaker.createPiece(PieceType.ROOK, ALLY_SIDE, new Position(5, 3)),
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(5, 1))
                ),
                ALLY_SIDE,
                new Position(5, 5),
                new Position(5, 0)
            ),
            Arguments.of(
                createPieces(
                    PieceMaker.createPiece(PieceType.ROOK, ALLY_SIDE, new Position(3, 5)),
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(1, 5))
                ),
                ALLY_SIDE,
                new Position(5, 5),
                new Position(0, 5)
            ),
            Arguments.of(
                createPieces(
                    PieceMaker.createPiece(PieceType.ROOK, ALLY_SIDE, new Position(6, 5)),
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(7, 5))
                ),
                ALLY_SIDE,
                new Position(5, 5),
                new Position(8, 5)
            )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치_내에_기물이_없다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(),
                ALLY_SIDE,
                new Position(1, 0),
                new Position(1, 4)
            ),
            Arguments.of(
                createPieces(),
                ALLY_SIDE,
                new Position(1, 0),
                new Position(1, 6)
            ),
            Arguments.of(
                createPieces(),
                ALLY_SIDE,
                new Position(1, 4),
                new Position(7, 4)
            ),
            Arguments.of(
                createPieces(),
                ALLY_SIDE,
                new Position(1, 0),
                new Position(8, 0)
            )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치_내에_Canon_이_아닌_한_가지_기물이_있다면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(7, 5))),
                ALLY_SIDE,
                new Position(5, 5),
                new Position(8, 5)
            ),
            Arguments.of(
                createPieces(PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(3, 3))),
                ALLY_SIDE,
                new Position(2, 3),
                new Position(4, 3)
            )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치_내에_Canon_인_한_가지_기물이_있다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(PieceMaker.createPiece(PieceType.CANNON, ENEMY_SIDE, new Position(7, 5))),
                ALLY_SIDE,
                new Position(5, 5),
                new Position(8, 5)
            ),
            Arguments.of(
                createPieces(PieceMaker.createPiece(PieceType.CANNON, ENEMY_SIDE, new Position(3, 3))),
                ALLY_SIDE,
                new Position(2, 3),
                new Position(4, 3)
            )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_아군_기물이_있다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(PieceMaker.createPiece(PieceType.CANNON, ALLY_SIDE, new Position(7, 5))),
                ALLY_SIDE,
                new Position(5, 5),
                new Position(8, 5)
            ),
            Arguments.of(
                createPieces(PieceMaker.createPiece(PieceType.CANNON, ENEMY_SIDE, new Position(4, 5))),
                ENEMY_SIDE,
                new Position(5, 5),
                new Position(3, 5)
            )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_Cannon_이_아닌_적_기물이_있다면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(3, 5)),
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(2, 5))
                ),
                ALLY_SIDE,
                new Position(5, 5),
                new Position(2, 5)
            ),
            Arguments.of(
                createPieces(
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(3, 5)),
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(7, 5))
                ),
                ALLY_SIDE,
                new Position(2, 5),
                new Position(7, 5)
            ),
            Arguments.of(
                createPieces(
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(3, 3)),
                    PieceMaker.createPiece(PieceType.ROOK, ENEMY_SIDE, new Position(3, 7))
                ),
                ALLY_SIDE,
                new Position(3, 2),
                new Position(3, 7)
            )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_Cannon_인_적_기물이_있다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(PieceMaker.createPiece(PieceType.CANNON, ENEMY_SIDE, new Position(4, 5))),
                ENEMY_SIDE,
                new Position(5, 5),
                new Position(3, 5)
            ),
            Arguments.of(
                createPieces(PieceMaker.createPiece(PieceType.CANNON, ENEMY_SIDE, new Position(4, 5))),
                ENEMY_SIDE,
                new Position(5, 5),
                new Position(3, 5)
            )
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 4", "1, 2, 4, 5"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_모두_다르면_움직일_수_없다(int x, int y, int moveX, int moveY) {
        assertThat(CANNON_MOVEMENT_STRATEGY.isLegalDestination(ALLY_SIDE, new Position(x, y),
            new Position(moveX, moveY))).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 2", "1, 2, 1, 4"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_하나만_다르면_움직일_수_있다(int x, int y, int moveX, int moveY) {
        assertThat(CANNON_MOVEMENT_STRATEGY.isLegalDestination(ALLY_SIDE, new Position(x, y),
            new Position(moveX, moveY))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치_내에_두_가지_이상의_기물이_있다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치_내에_두_가지_이상의_기물이_있다면_이동할_수_없다(Pieces map, Side side, Position origin, Position destination) {
        assertThat(CANNON_MOVEMENT_STRATEGY.isLegalPath(map, side, origin, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치_내에_기물이_없다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치_내에_기물이_없다면_이동할_수_없다(Pieces map, Side side, Position origin, Position destination) {
        assertThat(CANNON_MOVEMENT_STRATEGY.isLegalPath(map, side, origin, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치_내에_Canon_이_아닌_한_가지_기물이_있다면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_위치_내에_Canon_이_아닌_한_가지_기물이_있다면_이동할_수_있다(
        Pieces existingPieces,
        Side side,
        Position origin,
        Position destination
    ) {

        assertThat(CANNON_MOVEMENT_STRATEGY.isLegalPath(existingPieces, side, origin, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치_내에_Canon_인_한_가지_기물이_있다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치_내에_Canon_인_한_가지_기물이_있다면_이동할_수_없다(
        Pieces existingPieces,
        Side side,
        Position origin,
        Position destination
    ) {

        assertThat(CANNON_MOVEMENT_STRATEGY.isLegalPath(existingPieces, side, origin, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_아군_기물이_있다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치에_아군_기물이_있다면_이동할_수_없다(
        Pieces existingPieces,
        Side side,
        Position origin,
        Position destination
    ) {

        assertThat(CANNON_MOVEMENT_STRATEGY.isLegalPath(existingPieces, side, origin, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_Cannon_이_아닌_적_기물이_있다면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_위치에_Cannon_이_아닌_적_기물이_있다면_이동할_수_있다(
        Pieces existingPieces,
        Side side,
        Position origin,
        Position destination
    ) {

        assertThat(CANNON_MOVEMENT_STRATEGY.isLegalPath(existingPieces, side, origin, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_Cannon_인_적_기물이_있다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치에_Cannon_인_적_기물이_있다면_이동할_수_없다(
        Pieces existingPieces,
        Side side,
        Position origin,
        Position destination
    ) {

        assertThat(CANNON_MOVEMENT_STRATEGY.isLegalPath(existingPieces, side, origin, destination)).isFalse();
    }
}
