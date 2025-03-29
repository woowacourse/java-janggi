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

class ElephantTest {

    private final Elephant elephant = new Elephant(Country.CHO);
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

        @DisplayName("Elephant는 직-대-대 방향으로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void elephant(final JanggiPosition dest, final boolean expected) {
            // given
            final JanggiPosition now = new JanggiPosition(1, 1);
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = elephant.canMove(now, dest, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> elephant() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(4, 3), true),
                    Arguments.of(new JanggiPosition(1, 2), false)
            );
        }

        @DisplayName("Elephant는 직 또는 직-대 방향에 기물이 존재하면 이동할 수 없다.")
        @ParameterizedTest
        @MethodSource
        void elephant1(final JanggiPosition dest, final JanggiPosition hurdlePosition) {
            // given
            final JanggiPosition now = new JanggiPosition(2, 2);

            final Piece hurdle = new Chariot(Country.HAN);
            placePieceOnJanggiBoard(hurdlePosition, hurdle);

            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = elephant.canMove(now, dest, board);

            // then
            assertThat(actual).isFalse();
        }

        static Stream<Arguments> elephant1() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(4, 5), new JanggiPosition(2, 3)),
                    Arguments.of(new JanggiPosition(5, 4), new JanggiPosition(4, 3))
            );
        }
    }

}
