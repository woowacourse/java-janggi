package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.coordinate.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotTest {

    @DisplayName("Chariot은 직선 방향으로 모든 곳을 이동할 수 있다.")
    @Test
    void chariot() {
        // given
        final Piece chariotPiece = new Chariot(Country.CHO);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 2);
        final Position notAbleDest = new Position(2, 2);
        final Board board = new Board(new HashMap<>());

        // when
        final boolean actual1 = chariotPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = chariotPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("Chariot 은 목적지까지 어떠한 기물도 존재해서는 안된다")
    @Test
    void chariot2() {
        // given
        final Piece chariotPiece = new Chariot(Country.CHO);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 3);
        final Position notAbleDest = new Position(1, 5);
        final Map<Position, Piece> map = Map.of(new Position(1, 4),
                new Elephant(Country.CHO));
        final Board board = new Board(map);

        // when
        final boolean actual1 = chariotPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = chariotPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @Nested
    @DisplayName("출발지와 목적지가 일직선상에 존재하지 않으면서")
    class IsNotSameLine {
        @DisplayName("궁성 모서리에서 모서리로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void chariot2(final Position now, final Position destination) {
            // given
            final Piece piece = new Chariot(Country.HAN);
            final Board board = new Board(new HashMap<>());

            // when
            final boolean actual = piece.isAbleToMove(now, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> chariot2() {
            return Stream.of(
                    Arguments.of(new Position(3, 4), new Position(1, 6)),
                    Arguments.of(new Position(1, 4), new Position(3, 6)),
                    Arguments.of(new Position(1, 6), new Position(3, 4)),
                    Arguments.of(new Position(3, 6), new Position(1, 4))
            );
        }

        @DisplayName("궁성 모서리에서 모서리로 이동하는 경우, 궁성 중심에 기물이 존재하면 이동할 수 없다.")
        @ParameterizedTest
        @MethodSource
        void chariot3(final Position now, final Position destination) {
            // given
            final Piece piece = new Chariot(Country.HAN);
            final Board board = new Board(Map.of(new Position(2, 5), new Chariot(Country.HAN)));

            // when
            final boolean actual = piece.isAbleToMove(now, destination, board);

            // then
            assertThat(actual).isFalse();
        }

        static Stream<Arguments> chariot3() {
            return Stream.of(
                    Arguments.of(new Position(3, 4), new Position(1, 6)),
                    Arguments.of(new Position(1, 4), new Position(3, 6)),
                    Arguments.of(new Position(1, 6), new Position(3, 4)),
                    Arguments.of(new Position(3, 6), new Position(1, 4))
            );
        }

        @DisplayName("궁성 중심에서 모서리로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void chariot4(final Position now, final Position destination) {
            // given
            final Piece piece = new Chariot(Country.HAN);
            final Board board = new Board(new HashMap<>());

            // when
            final boolean actual = piece.isAbleToMove(now, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> chariot4() {
            return Stream.of(
                    Arguments.of(new Position(2, 5), new Position(1, 6)),
                    Arguments.of(new Position(2, 5), new Position(3, 6)),
                    Arguments.of(new Position(2, 5), new Position(3, 4)),
                    Arguments.of(new Position(2, 5), new Position(1, 4))
            );
        }

        @DisplayName("궁성 모서리에서 중심으로 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource
        void chariot5(final Position now, final Position destination) {
            // given
            final Piece piece = new Chariot(Country.HAN);
            final Board board = new Board(new HashMap<>());

            // when
            final boolean actual = piece.isAbleToMove(now, destination, board);

            // then
            assertThat(actual).isTrue();
        }

        static Stream<Arguments> chariot5() {
            return Stream.of(
                    Arguments.of(new Position(1, 6), new Position(2, 5)),
                    Arguments.of(new Position(3, 6), new Position(2, 5)),
                    Arguments.of(new Position(3, 4), new Position(2, 5)),
                    Arguments.of(new Position(1, 4), new Position(2, 5))
            );
        }
    }

}
