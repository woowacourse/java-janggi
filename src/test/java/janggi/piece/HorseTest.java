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

class HorseTest {

    private final Piece horse = new Horse(Country.CHO);
    private final Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();

    @BeforeEach
    void initJanggiBoard() {
        janggiBoard.clear();
    }

    public void placePieceOnJanggiBoard(final JanggiPosition janggiPosition, final Piece piece){
        janggiBoard.put(janggiPosition, piece);
    }

    @Nested
    @DisplayName("마 이동 로직")
    class CanMove {

        @DisplayName("horse는 직-대 방향으로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void horse(final JanggiPosition dest, final boolean expected) {
            // given
            final JanggiPosition now = new JanggiPosition(1, 1);

            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = horse.canMove(now, dest, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> horse() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(3, 2), true),
                    Arguments.of(new JanggiPosition(1, 2), false)
            );
        }

        @DisplayName("horse는 직 방향에 기물이 존재하면 이동할 수 없다.")
        @Test
        void horse1() {
            // given
            final JanggiPosition now = new JanggiPosition(2, 3);
            final JanggiPosition dest = new JanggiPosition(1, 1);

            final Cannon hurdle = new Cannon(Country.HAN);
            placePieceOnJanggiBoard(new JanggiPosition(2, 2), hurdle);
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = hurdle.canMove(now, dest, board);

            // then
            assertThat(actual).isFalse();
        }
    }

}
