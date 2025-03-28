package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import java.util.HashMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GeneralTest {


    @Nested
    @DisplayName("장의 움직임")
    class CanMove {
        @DisplayName("General은 주변 한칸으로 이동할 수 있다.")
        @Test
        void general() {
            // given
            final Piece generalPiece = new General(Country.CHO);
            final JanggiPosition now = new JanggiPosition(9, 4);
            final JanggiPosition ableDest = new JanggiPosition(9, 5);
            final JanggiPosition notAbleDest = new JanggiPosition(8, 5);
            final Board board = new Board(new HashMap<>());
            // when
            final boolean actual1 = generalPiece.isAbleToMove(now, ableDest, board);
            final boolean actual2 = generalPiece.isAbleToMove(now, notAbleDest, board);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("General이 궁성 외부로 나갈 수 없다.")
        @Test
        void general1() {
            // given
            final Piece generalPiece = new General(Country.CHO);
            final JanggiPosition now = new JanggiPosition(9, 6);
            final JanggiPosition notAbleDest = new JanggiPosition(9, 7);
            final Board board = new Board(new HashMap<>());

            // when
            final boolean actual = generalPiece.canMove(now, notAbleDest, board);

            // then
            assertThat(actual).isFalse();
        }

        @DisplayName("General은 중심 -> 모서리, 모서리 -> 중심으로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void general2(final JanggiPosition source, final JanggiPosition destination) {
            // given
            final Piece generalPiece = new General(Country.CHO);
            final Board board = new Board(new HashMap<>());

            // when
            final boolean actual = generalPiece.canMove(source, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> general2(){
            return Stream.of(
                    Arguments.of(new JanggiPosition(9, 5), new JanggiPosition(8, 6)),
                    Arguments.of(new JanggiPosition(8, 4), new JanggiPosition(9, 5))
            );
        }

        @DisplayName("General은 자신의 나라 궁성 밖으로 이동할 수 없다.")
        @ParameterizedTest
        @MethodSource
        void general3(final JanggiPosition source, final JanggiPosition destination, final Country country) {
            // given
            final Piece generalPiece = new General(country);
            final Board board = new Board(new HashMap<>());

            // when
            final boolean actual = generalPiece.canMove(source, destination, board);

            // then
            assertThat(actual).isFalse();
        }

        static Stream<Arguments> general3(){
            return Stream.of(
                    Arguments.of(new JanggiPosition(9, 5), new JanggiPosition(1, 6), Country.CHO),
                    Arguments.of(new JanggiPosition(8, 6), new JanggiPosition(2, 5), Country.CHO),
                    Arguments.of(new JanggiPosition(2, 5), new JanggiPosition(8, 6), Country.HAN),
                    Arguments.of(new JanggiPosition(1, 4), new JanggiPosition(9, 5), Country.HAN)
            );
        }
    }
}
