package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotTest {

    private final Chariot chariot = new Chariot(Country.CHO);
    private final Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();

    @BeforeEach
    void initJanggiBoard() {
        janggiBoard.clear();
    }

    public void placePieceOnJanggiBoard(final JanggiPosition janggiPosition, final Piece piece) {
        janggiBoard.put(janggiPosition, piece);
    }

    @Nested
    @DisplayName("이동 로직")
    class CanMove {
        @DisplayName("Chariot은 직선 방향으로 모든 곳을 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void chariot(final JanggiPosition dest, final boolean expected) {
            // given
            final JanggiPosition now = new JanggiPosition(1, 1);
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = chariot.canMove(now, dest, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> chariot() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 2), true),
                    Arguments.of(new JanggiPosition(2, 2), false)
            );
        }

        @DisplayName("Chariot은 목적지까지 어떠한 기물도 존재해서는 안된다")
        @ParameterizedTest
        @MethodSource
        void chariot2(final JanggiPosition dest, final boolean expected) {
            // given
            final JanggiPosition now = new JanggiPosition(1, 1);
            placePieceOnJanggiBoard(new JanggiPosition(1, 4), new Elephant(Country.CHO));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = chariot.canMove(now, dest, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> chariot2() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 3), true),
                    Arguments.of(new JanggiPosition(1, 5), false)
            );
        }

    }

    @Nested
    @DisplayName("출발지와 목적지가 일직선상에 존재하지 않으면서")
    class IsNotSameLine {
        @DisplayName("궁성 모서리에서 모서리로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void chariot2(final JanggiPosition now, final JanggiPosition destination) {
            // given
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = chariot.canMove(now, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> chariot2() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(3, 4), new JanggiPosition(1, 6)),
                    Arguments.of(new JanggiPosition(1, 4), new JanggiPosition(3, 6)),
                    Arguments.of(new JanggiPosition(1, 6), new JanggiPosition(3, 4)),
                    Arguments.of(new JanggiPosition(3, 6), new JanggiPosition(1, 4))
            );
        }

        @DisplayName("궁성 모서리에서 모서리로 이동하는 경우, 궁성 중심에 기물이 존재하면 이동할 수 없다.")
        @ParameterizedTest
        @MethodSource
        void chariot3(final JanggiPosition now, final JanggiPosition destination) {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(2, 5), new Chariot(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = chariot.canMove(now, destination, board);

            // then
            assertThat(actual).isFalse();
        }

        static Stream<Arguments> chariot3() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(3, 4), new JanggiPosition(1, 6)),
                    Arguments.of(new JanggiPosition(1, 4), new JanggiPosition(3, 6)),
                    Arguments.of(new JanggiPosition(1, 6), new JanggiPosition(3, 4)),
                    Arguments.of(new JanggiPosition(3, 6), new JanggiPosition(1, 4))
            );
        }

        @DisplayName("궁성 중심에서 모서리로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void chariot4(final JanggiPosition now, final JanggiPosition destination) {
            // given
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = chariot.canMove(now, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> chariot4() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(2, 5), new JanggiPosition(1, 6)),
                    Arguments.of(new JanggiPosition(2, 5), new JanggiPosition(3, 6)),
                    Arguments.of(new JanggiPosition(2, 5), new JanggiPosition(3, 4)),
                    Arguments.of(new JanggiPosition(2, 5), new JanggiPosition(1, 4))
            );
        }

        @DisplayName("궁성 모서리에서 중심으로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void chariot5(final JanggiPosition now, final JanggiPosition destination) {
            // given
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = chariot.isAbleToMove(now, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> chariot5() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 6), new JanggiPosition(2, 5)),
                    Arguments.of(new JanggiPosition(3, 6), new JanggiPosition(2, 5)),
                    Arguments.of(new JanggiPosition(3, 4), new JanggiPosition(2, 5)),
                    Arguments.of(new JanggiPosition(1, 4), new JanggiPosition(2, 5))
            );
        }
    }

}
