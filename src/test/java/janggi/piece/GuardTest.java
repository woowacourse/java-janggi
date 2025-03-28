package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.coordinate.Position;
import java.util.HashMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GuardTest {

    @Nested
    @DisplayName("사 이동")
    class CanMove {
        @DisplayName("guard는 주변 한칸으로 이동할 수 있다.")
        @Test
        void guard() {
            // given
            final Piece guardPiece = new Guard(Country.CHO);
            final Position now = new Position(9, 4);
            final Position ableDest = new Position(9, 5);
            final Position notAbleDest = new Position(8, 5);
            final Board board = new Board(new HashMap<>());

            // when
            final boolean actual1 = guardPiece.isAbleToMove(now, ableDest, board);
            final boolean actual2 = guardPiece.isAbleToMove(now, notAbleDest, board);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(actual1).isTrue(),
                    () -> assertThat(actual2).isFalse()
            );
        }

        @DisplayName("guard는 궁성 밖으로 나갈 수 없다.")
        @Test
        void guard1() {
            // given
            final Piece guardPiece = new Guard(Country.HAN);
            final Position now = new Position(2, 4);
            final Position notAbleDest = new Position(2, 3);
            final Board board = new Board(new HashMap<>());

            // when
            final boolean actual = guardPiece.canMove(now, notAbleDest, board);

            // then
            assertThat(actual).isFalse();
        }

        @DisplayName("Guard는 중심 -> 모서리, 모서리 -> 중심으로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void guard2(final Position source, final Position destination) {
            // given
            final Piece generalPiece = new Guard(Country.CHO);
            final Board board = new Board(new HashMap<>());

            // when
            final boolean actual = generalPiece.canMove(source, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> guard2(){
            return Stream.of(
                    Arguments.of(new Position(9, 5), new Position(8, 6)),
                    Arguments.of(new Position(8, 4), new Position(9, 5))
            );
        }
    }
}
