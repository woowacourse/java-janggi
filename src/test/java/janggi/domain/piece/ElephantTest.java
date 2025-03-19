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
import static org.assertj.core.api.Assertions.assertThatCode;

class ElephantTest {

    public static Stream<Arguments> 이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(5, 6))),
                7,
                8
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(6, 7))),
                7,
                8
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(5, 6))),
                3,
                8
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(4, 7))),
                3,
                8
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(6, 5))),
                8,
                7
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(7, 6))),
                8,
                7
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(6, 5))),
                8,
                3
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(7, 4))),
                8,
                3
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(5, 4))),
                7,
                2
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(6, 3))),
                7,
                2
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(5, 4))),
                3,
                2
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(4, 3))),
                3,
                2
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(4, 5))),
                2,
                3
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(3, 4))),
                2,
                3
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(4, 5))),
                2,
                7
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(3, 6))),
                2,
                7
            )
        );
    }

    public static Stream<Arguments> 이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                7,
                8
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                7,
                8
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                3,
                8
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                3,
                8
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                8,
                7
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                8,
                7
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                8,
                3
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                8,
                3
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                7,
                2
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                7,
                2
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                3,
                2
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                3,
                2
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                2,
                3
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                2,
                3
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                2,
                7
            ),
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (),
                2,
                7
            )
        );
    }

    public static Stream<Arguments> 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.HAN, new Position(7, 8))),
                7,
                8
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.HAN, new Position(3, 8))),
                3,
                8
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.HAN, new Position(8, 7))),
                8,
                7
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.HAN, new Position(8, 3))),
                8,
                3
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.HAN, new Position(7, 2))),
                7,
                2
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.HAN, new Position(3, 2))),
                3,
                2
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.HAN, new Position(2, 3))),
                2,
                3
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.HAN, new Position(2, 7))),
                2,
                7
            )
        );
    }

    public static Stream<Arguments> 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(7, 8))),
                7,
                8
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(3, 8))),
                3,
                8
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(8, 7))),
                8,
                7
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(8, 3))),
                8,
                3
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(7, 2))),
                7,
                2
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(3, 2))),
                3,
                2
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(2, 3))),
                2,
                3
            ),

            Arguments.of(
                new Elephant(Side.CHO, new Position(5, 5)),
                List.of (new Elephant(Side.CHO, new Position(2, 7))),
                2,
                7
            )
        );
    }

    public static Stream<Arguments> 이동_가능한_경로가_좌표_밖에_있다면_계산되지_않는다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                new Elephant(Side.CHO, new Position(0,0)),
                2,
                3
            ),
            Arguments.of(
                new Elephant(Side.HAN, new Position(8,0)),
                6,
                3
            ),
            Arguments.of(
                new Elephant(Side.HAN, new Position(8,9)),
                6,
                6
            ),
            Arguments.of(
                new Elephant(Side.HAN, new Position(0,9)),
                2,
                6
            )
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 4", "1, 2, -1, 4", "1, 2, 3, 0", "1, 2, -1, 0"})
    void 이동하고자_하는_x와의_차이가_2인_경우_이동하고자_하는_y와의_차이가_3이_아니라면_움직일_수_없다(
            int x,
            int y,
            int moveX,
            int moveY
    ) {

        Elephant elephant = new Elephant(Side.CHO, new Position(x, y));

        assertThat(elephant.isMoveablePosition(moveX, moveY)).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 5", "1, 2, -1, 5", "1, 2, 3, -1", "1, 2, -1, -1"})
    void 이동하고자_하는_x와의_차이가_2인_경우_이동하고자_하는_y와의_차이가_3이라면_움직일_수_있다(
            int x,
            int y,
            int moveX,
            int moveY
    ) {

        Elephant elephant = new Elephant(Side.CHO, new Position(x, y));

        assertThat(elephant.isMoveablePosition(moveX, moveY)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 4, 3", "1, 2, -2, 3", "1, 2, 4, 1", "1, 2, -2, 1"})
    void 이동하고자_하는_x와의_차이가_3인_경우_이동하고자_하는_y와의_차이가_2가_아니라면_움직일_수_없다(
            int x,
            int y,
            int moveX,
            int moveY
    ) {

        Elephant elephant = new Elephant(Side.CHO, new Position(x, y));

        assertThat(elephant.isMoveablePosition(moveX, moveY)).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 4, 4", "1, 2, -2, 4", "1, 2, 4, 0", "1, 2, -2, 0"})
    void 이동하고자_하는_x와의_차이가_3인_경우_이동하고자_하는_y와의_차이가_2라면_움직일_수_있다(
            int x,
            int y,
            int moveX,
            int moveY
    ) {

        Elephant elephant = new Elephant(Side.CHO, new Position(x, y));

        assertThat(elephant.isMoveablePosition(moveX, moveY)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다(Elephant elephant, List<Piece> existingPieces, int x, int y) {
        assertThat(elephant.isMoveablePath(existingPieces, x, y)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다(Elephant elephant, List<Piece> existingPieces, int x, int y) {
        assertThat(elephant.isMoveablePath(existingPieces, x, y)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다(Elephant elephant, List<Piece> existingPieces, int x, int y) {
        assertThat(elephant.isMoveablePath(existingPieces, x, y)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다(Elephant elephant, List<Piece> existingPieces, int x, int y) {
        assertThat(elephant.isMoveablePath(existingPieces, x, y)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동_가능한_경로가_좌표_밖에_있다면_계산되지_않는다_테스트_케이스")
    void 이동_가능한_경로가_좌표_밖에_있다면_계산되지_않는다(Elephant elephant, int x, int y) {
        assertThatCode(() -> elephant.move(List.of(), x, y)).doesNotThrowAnyException();
    }
}
