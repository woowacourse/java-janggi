package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RookTest {

    private static Stream<Arguments> 이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(1, 5))),
                        1,
                        7
                ),
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(4, 2))),
                        6,
                        2
                ),
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(5, 2))),
                        5,
                        2
                ),
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(2, 2))),
                        3,
                        2
                ),
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(1, 3))),
                        1,
                        4
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(3, 5))),
                        1,
                        7
                ),
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(4, 1))),
                        6,
                        2
                ),
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(5, 1))),
                        5,
                        2
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.HAN, new Position(3, 2))),
                        3,
                        2
                ),
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.HAN, new Position(4, 2))),
                        4,
                        2
                ),
                Arguments.of(
                        new Rook(Side.HAN, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(5, 2))),
                        5,
                        2
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Rook(Side.HAN, new Position(1, 2)),
                        List.of(new Rook(Side.HAN, new Position(3, 2))),
                        3,
                        2
                ),
                Arguments.of(
                        new Rook(Side.HAN, new Position(1, 2)),
                        List.of(new Rook(Side.HAN, new Position(4, 2))),
                        4,
                        2
                ),
                Arguments.of(
                        new Rook(Side.CHO, new Position(1, 2)),
                        List.of(new Rook(Side.CHO, new Position(5, 2))),
                        5,
                        2
                )
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 4", "1, 2, 4, 5"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_모두_다르면_움직일_수_없다(int x, int y, int moveX, int moveY) {
        Rook rook = new Rook(Side.CHO, new Position(x, y));

        assertThat(rook.isMoveablePosition(new Position(moveX, moveY))).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 2", "1, 2, 1, 4"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_하나만_다르면_움직일_수_있다(int x, int y, int moveX, int moveY) {
        Rook rook = new Rook(Side.CHO, new Position(x, y));

        assertThat(rook.isMoveablePosition(new Position(moveX, moveY))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다(Rook rook, List<Piece> existingPieces, int x, int y) {
        assertThat(rook.isMoveablePath(existingPieces, new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다(Rook rook, List<Piece> existingPieces, int x, int y) {
        assertThat(rook.isMoveablePath(existingPieces, new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다(Rook rook, List<Piece> existingPieces, int x, int y) {
        assertThat(rook.isMoveablePath(existingPieces, new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다(Rook rook, List<Piece> existingPieces, int x, int y) {
        assertThat(rook.isMoveablePath(existingPieces, new Position(x, y))).isFalse();
    }
}
