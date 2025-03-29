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

class SoldierTest {

    private final Piece soldierOfHan = new Soldier(Country.HAN);
    private final Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();

    @BeforeEach
    void initJanggiBoard() {
        janggiBoard.clear();
    }

    @Nested
    @DisplayName("기물 이동 로직")
    class CanMove {

        @DisplayName("Soldier는 뒷 방향을 제외하고 한 칸을 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void soldiers(final JanggiPosition dest, final boolean expected) {

            // given
            final JanggiPosition now = new JanggiPosition(2, 2);
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = soldierOfHan.canMove(now, dest, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> soldiers() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(3, 2), true),
                    Arguments.of(new JanggiPosition(1, 2), false)
            );
        }

        @DisplayName("Soldier은 상대 궁성에서 뒷 방향을 제외하고 대각 -> 중심, 중심 -> 대각으로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void soldier1(final JanggiPosition source, final JanggiPosition destination, final Country country) {
            // given
            final Piece soldier = new Soldier(country);
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = soldier.canMove(source, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> soldier1() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(8, 4), new JanggiPosition(9, 5), Country.HAN),
                    Arguments.of(new JanggiPosition(9, 5), new JanggiPosition(10, 6), Country.HAN),
                    Arguments.of(new JanggiPosition(3, 4), new JanggiPosition(2, 5), Country.CHO),
                    Arguments.of(new JanggiPosition(2, 5), new JanggiPosition(1, 6), Country.CHO)
            );
        }
    }

}
