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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GuardTest {

    private final Guard guard = new Guard(Country.CHO);
    private final Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();

    @BeforeEach
    void initJanggiBoard() {
        janggiBoard.clear();
    }

    @Nested
    @DisplayName("사 이동")
    class CanMove {
        @DisplayName("guard는 주변 한칸으로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void guard(final JanggiPosition dest, final boolean expected) {
            // given
            final JanggiPosition now = new JanggiPosition(9, 4);

            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = guard.canMove(now, dest, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> guard() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(9, 5), true),
                    Arguments.of(new JanggiPosition(8, 5), false)
            );
        }

        @DisplayName("guard는 궁성 밖으로 나갈 수 없다.")
        @Test
        void guard1() {
            // given
            final JanggiPosition now = new JanggiPosition(9, 4);
            final JanggiPosition dest = new JanggiPosition(9, 3);

            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = guard.canMove(now, dest, board);

            // then
            assertThat(actual).isFalse();
        }

        @DisplayName("Guard는 중심 -> 모서리, 모서리 -> 중심으로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void guard2(final JanggiPosition source, final JanggiPosition destination) {
            // given
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = guard.canMove(source, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> guard2() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(9, 5), new JanggiPosition(8, 6)),
                    Arguments.of(new JanggiPosition(8, 4), new JanggiPosition(9, 5))
            );
        }
    }
}
