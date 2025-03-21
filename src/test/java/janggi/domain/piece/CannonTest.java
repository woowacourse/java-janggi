package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class CannonTest {

    private static Stream<Arguments> 이동하고자_하는_위치_내에_두_가지_이상의_기물이_있다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.CHO, 5, 7), new Rook(Side.HAN, 5, 8)),
                        5, 9
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.CHO, 5, 3), new Rook(Side.HAN, 5, 1)),
                        5, 0
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.CHO, 3, 5), new Rook(Side.HAN, 1, 5)),
                        0, 5
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.CHO, 6, 5), new Rook(Side.HAN, 7, 5)),
                        8, 5
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치_내에_기물이_없다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        7, 5
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        2, 5
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        5, 7
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        5, 3
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치_내에_Canon_이_아닌_한_가지_기물이_있다면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.HAN, 7, 5)),
                        8, 5
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.CHO, 7, 5)),
                        8, 5
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.HAN, 5, 7)),
                        5, 9
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.CHO, 5, 7)),
                        5, 9
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.HAN, 3, 5)),
                        0, 5
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.CHO, 3, 5)),
                        0, 5
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.HAN, 5, 3)),
                        5, 1
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Rook(Side.CHO, 5, 3)),
                        5, 1
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치_내에_Canon_인_한_가지_기물이_있다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Cannon(Side.HAN, 7, 5)),
                        8, 5
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Cannon(Side.CHO, 7, 5)),
                        8, 5
                ),

                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Cannon(Side.HAN, 5, 7)),
                        5, 8
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Cannon(Side.CHO, 5, 7)),
                        5, 8
                ),

                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Cannon(Side.HAN, 3, 5)),
                        0, 5
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Cannon(Side.CHO, 3, 5)),
                        0, 5
                ),

                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Cannon(Side.HAN, 5, 3)),
                        5, 0
                ),
                Arguments.of(
                        new Cannon(Side.CHO, 5, 5),
                        List.of(new Cannon(Side.CHO, 5, 3)),
                        5, 0
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_아군_기물이_있다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.CHO, 6, 5),
                                new Rook(Side.HAN, 8, 5)
                        ),
                        8, 5
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.HAN, 3, 5),
                                new Rook(Side.HAN, 1, 5)
                        ),
                        1, 5
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.CHO, 5, 7),
                                new Rook(Side.HAN, 5, 9)
                        ),
                        5, 9
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.HAN, 5, 7),
                                new Rook(Side.HAN, 5, 9)
                        ),
                        5, 9
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.HAN, 5, 3),
                                new Rook(Side.HAN, 5, 1)
                        ),
                        5, 1
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.CHO, 5, 3),
                                new Rook(Side.HAN, 5, 1)
                        ),
                        5, 1
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_Cannon_이_아닌_적_기물이_있다면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.CHO, 6, 5),
                                new Rook(Side.CHO, 8, 5)
                        ),
                        8, 5
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.HAN, 3, 5),
                                new Rook(Side.CHO, 1, 5)
                        ),
                        1, 5
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.CHO, 5, 7),
                                new Rook(Side.CHO, 5, 9)
                        ),
                        5, 9
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.HAN, 5, 7),
                                new Rook(Side.CHO, 5, 9)
                        ),
                        5, 9
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.HAN, 5, 3),
                                new Rook(Side.CHO, 5, 1)
                        ),
                        5, 1
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.CHO, 5, 3),
                                new Rook(Side.CHO, 5, 1)
                        ),
                        5, 1
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_Cannon_인_적_기물이_있다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.CHO, 6, 5),
                                new Cannon(Side.CHO, 8, 5)
                        ),
                        8, 5
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.HAN, 3, 5),
                                new Cannon(Side.CHO, 1, 5)
                        ),
                        1, 5
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.CHO, 5, 7),
                                new Cannon(Side.CHO, 5, 9)
                        ),
                        5, 9
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.HAN, 5, 7),
                                new Cannon(Side.CHO, 5, 9)
                        ),
                        5, 9
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.HAN, 5, 3),
                                new Cannon(Side.CHO, 5, 1)
                        ),
                        5, 1
                ),
                Arguments.of(
                        new Cannon(Side.HAN, 5, 5),
                        List.of(
                                new Rook(Side.CHO, 5, 3),
                                new Cannon(Side.CHO, 5, 1)
                        ),
                        5, 1
                )
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 4", "1, 2, 4, 5"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_모두_다르면_움직일_수_없다(int x, int y, int moveX, int moveY) {
        Cannon cannon = new Cannon(Side.CHO, x, y);

        assertThat(cannon.isMoveablePosition(new Position(moveX, moveY))).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 2", "1, 2, 1, 4"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_하나만_다르면_움직일_수_있다(int x, int y, int moveX, int moveY) {
        Cannon cannon = new Cannon(Side.CHO, x, y);

        assertThat(cannon.isMoveablePosition(new Position(moveX, moveY))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치_내에_두_가지_이상의_기물이_있다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치_내에_두_가지_이상의_기물이_있다면_이동할_수_없다(Cannon cannon, List<Piece> existingPieces, int x, int y) {
        assertThat(cannon.isMoveablePath(existingPieces, new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치_내에_기물이_없다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치_내에_기물이_없다면_이동할_수_없다(Cannon cannon, int x, int y) {
        assertThat(cannon.isMoveablePath(List.of(), new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치_내에_Canon_이_아닌_한_가지_기물이_있다면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_위치_내에_Canon_이_아닌_한_가지_기물이_있다면_이동할_수_있다(
            Cannon cannon,
            List<Piece> existingPieces,
            int x,
            int y
    ) {
        assertThat(cannon.isMoveablePath(existingPieces, new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치_내에_Canon_인_한_가지_기물이_있다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치_내에_Canon_인_한_가지_기물이_있다면_이동할_수_없다(
            Cannon cannon,
            List<Piece> existingPieces,
            int x,
            int y
    ) {
        assertThat(cannon.isMoveablePath(existingPieces, new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_아군_기물이_있다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치에_아군_기물이_있다면_이동할_수_없다(
            Cannon cannon,
            List<Piece> existingPieces,
            int x,
            int y
    ) {
        assertThat(cannon.isMoveablePath(existingPieces, new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_Cannon_이_아닌_적_기물이_있다면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_위치에_Cannon_이_아닌_적_기물이_있다면_이동할_수_있다(
            Cannon cannon,
            List<Piece> existingPieces,
            int x,
            int y
    ) {
        assertThat(cannon.isMoveablePath(existingPieces, new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_Cannon_인_적_기물이_있다면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치에_Cannon_인_적_기물이_있다면_이동할_수_없다(
            Cannon cannon,
            List<Piece> existingPieces,
            int x,
            int y
    ) {
        assertThat(cannon.isMoveablePath(existingPieces, new Position(x, y))).isFalse();
    }
}
