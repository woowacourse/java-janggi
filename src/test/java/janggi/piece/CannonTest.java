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

class CannonTest {

    private final Cannon cannon = new Cannon(Country.CHO);
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

        @DisplayName("Cannon은 목적지가 같은 라인이 아니라면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void cannon1(final JanggiPosition dest, final boolean expected) {
            // given
            final JanggiPosition now = new JanggiPosition(1, 1);
            placePieceOnJanggiBoard(new JanggiPosition(1, 2), new General(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = cannon.canMove(now, dest, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> cannon1() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 3), true),
                    Arguments.of(new JanggiPosition(2, 2), false)
            );
        }

        @DisplayName("Cannon은 목적지까지 가는 길에 기물이 하나도 없다면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void cannon2(final JanggiPosition dest, final boolean expected) {
            // given
            final JanggiPosition now = new JanggiPosition(1, 1);
            placePieceOnJanggiBoard(new JanggiPosition(1, 2), new Chariot(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = cannon.canMove(now, dest, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> cannon2() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 3), true),
                    Arguments.of(new JanggiPosition(2, 1), false)
            );
        }

        @DisplayName("Cannon은 목적지까지 가는 길에 포가 있다면 false를 반환한다.")
        @Test
        void cannon3() {
            // given
            final JanggiPosition now = new JanggiPosition(1, 1);
            final JanggiPosition dest = new JanggiPosition(1, 3);
            placePieceOnJanggiBoard(new JanggiPosition(1, 2), new Cannon(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = cannon.canMove(now, dest, board);

            // then
            assertThat(actual).isFalse();
        }

        @DisplayName("Cannon은 목적지까지 가는 길에 2개 이상이 있다면 false를 반환한다.")
        @Test
        void cannon4() {
            // given
            final JanggiPosition now = new JanggiPosition(1, 1);
            final JanggiPosition dest = new JanggiPosition(1, 4);
            placePieceOnJanggiBoard(new JanggiPosition(1, 2), new Chariot(Country.HAN));
            placePieceOnJanggiBoard(new JanggiPosition(1, 3), new Chariot(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = cannon.canMove(now, dest, board);

            // then
            assertThat(actual).isFalse();
        }

        @DisplayName("포는 포를 죽일 수 없다.")
        @Test
        void cannon5() {
            // given
            final JanggiPosition now = new JanggiPosition(1, 1);
            final JanggiPosition dest = new JanggiPosition(1, 4);
            placePieceOnJanggiBoard(new JanggiPosition(1, 4), new Cannon(Country.CHO));
            placePieceOnJanggiBoard(new JanggiPosition(1, 3), new Chariot(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = cannon.canMove(now, dest, board);

            // then
            assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("궁성 내부 추가 로직")
    class isInPalace {

        @DisplayName("출발지와 목적지가 일직선상에 존재하지 않으면서, 궁성 모서리에서 모서리로 이동하는 경우 궁성 중심에 포가 아닌 기물이 존재해야 한다.")
        @ParameterizedTest
        @MethodSource
        void cannon6(final JanggiPosition now, final JanggiPosition dest, final Piece centerPieceInPalace,
                     final boolean expected) {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(2, 5), centerPieceInPalace);
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = cannon.canMove(now, dest, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> cannon6() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 4), new JanggiPosition(3, 6), new Chariot(Country.CHO), true),
                    Arguments.of(new JanggiPosition(1, 4), new JanggiPosition(3, 7), new Cannon(Country.CHO), false)
            );
        }

        @DisplayName("궁성 모서리에서, 모서리를 지나 궁성 외부로 가는 경우에는 대각선 이동이 불가능하다.")
        @Test
        void cannon7() {
            // given
            final JanggiPosition now = new JanggiPosition(1, 4);
            final JanggiPosition dest = new JanggiPosition(4, 7);
            placePieceOnJanggiBoard(new JanggiPosition(2, 5), new Chariot(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = cannon.canMove(now, dest, board);

            // then
            assertThat(actual).isFalse();
        }

    }


}
