package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class TankTest {

    @Nested
    @DisplayName("일반 이동 테스트")
    class NormalMoveTest {
        @DisplayName("상하좌우로 이동하면 true를 반환한다.")
        @Test
        void shouldReturnTrueWhenFollowMovingRule() {
            // given
            Tank tank = new Tank(Side.RED);
            Position start = new Position(5, 5);
            Position end = new Position(8, 5);

            // when
            boolean canMove = tank.canMove(start, end, Map.of());

            // then
            assertThat(canMove).isTrue();
        }

        @DisplayName("경로 상에 다른 말이 존재하면 false를 반환한다.")
        @Test
        void shouldReturnFalseWhenPieceOnPath() {
            // given
            Tank tank = new Tank(Side.RED);
            Position start = new Position(5, 5);
            Position end = new Position(8, 5);
            Map<Position, Piece> existsPieceOnPathBoard = Map.of(new Position(7, 5), new Tank(Side.BLUE));

            // when
            boolean canMove = tank.canMove(start, end, existsPieceOnPathBoard);

            // then
            assertThat(canMove).isFalse();
        }

        @DisplayName("상하좌우로 이동하지 않으면 false를 반환한다.")
        @ParameterizedTest
        @CsvSource(value = {
                "6, 6",
                "4, 6",
                "6, 4",
                "4, 4"
        })
        void shouldReturnFalseWhenInvalidMovingRule(int destX, int destY) {
            // given
            Tank tank = new Tank(Side.RED);
            Position start = new Position(5, 5);
            Position end = new Position(destX, destY);

            // when
            boolean canMove = tank.canMove(start, end, Map.of());

            // then
            assertThat(canMove).isFalse();
        }
    }

    @Nested
    @DisplayName("궁성 내 이동 테스트")
    class PalaceMoveTest {

        @DisplayName("BLUE 궁성 내에서 대각선으로 이동 가능하면 true를 반환한다.")
        @ParameterizedTest
        @MethodSource("validDiagonalMoveInBluePalace")
        void shouldReturnTrueWhenMoveDiagonalInBluePalace(Position start, Position end) {
            // given
            Tank tank = new Tank(Side.BLUE);
            Map<Position, Piece> emptyInPalaceBoard = Map.of();

            // when
            boolean canMove = tank.canMove(start, end, emptyInPalaceBoard);

            // then
            assertThat(canMove).isTrue();
        }

        @DisplayName("RED 궁성 내에서 대각선으로 이동 가능하면 true를 반환한다.")
        @ParameterizedTest
        @MethodSource("validDiagonalMoveInRedPalace")
        void shouldReturnTrueWhenMoveDiagonalInRedPalace(Position start, Position end) {
            // given
            Tank tank = new Tank(Side.BLUE);
            Map<Position, Piece> emptyInPalaceBoard = Map.of();

            // when
            boolean canMove = tank.canMove(start, end, emptyInPalaceBoard);

            // then
            assertThat(canMove).isTrue();
        }

        private static Stream<Arguments> validDiagonalMoveInBluePalace() {
            return Stream.of(
                    Arguments.of(new Position(4, 1), new Position(6, 3)),
                    Arguments.of(new Position(6, 3), new Position(4, 1)),
                    Arguments.of(new Position(4, 3), new Position(6, 1)),
                    Arguments.of(new Position(6, 1), new Position(4, 3)),
                    Arguments.of(new Position(4, 1), new Position(5, 2)),
                    Arguments.of(new Position(6, 3), new Position(5, 2)),
                    Arguments.of(new Position(4, 3), new Position(5, 2)),
                    Arguments.of(new Position(6, 1), new Position(5, 2))
            );
        }

        private static Stream<Arguments> validDiagonalMoveInRedPalace() {
            return Stream.of(
                    Arguments.of(new Position(4, 8), new Position(6, 10)),
                    Arguments.of(new Position(6, 10), new Position(4, 8)),
                    Arguments.of(new Position(4, 10), new Position(6, 8)),
                    Arguments.of(new Position(6, 8), new Position(4, 10)),
                    Arguments.of(new Position(4, 8), new Position(5, 9)),
                    Arguments.of(new Position(6, 10), new Position(5, 9)),
                    Arguments.of(new Position(4, 10), new Position(5, 9)),
                    Arguments.of(new Position(6, 8), new Position(5, 9))
            );
        }


        @DisplayName("BLUE 궁성 내에서 대각선으로 이동 불가능한 위치에선 false를 반환한다.")
        @ParameterizedTest
        @MethodSource("invalidDiagonalMoveInBluePalace")
        void shouldReturnFalseWhereCantMoveDiagonalPositionInBluePalace(Position start, Position end) {
            // given
            Tank tank = new Tank(Side.BLUE);
            Map<Position, Piece> emptyInPalaceBoard = Map.of();

            // when
            boolean canMove = tank.canMove(start, end, emptyInPalaceBoard);

            // then
            assertThat(canMove).isFalse();
        }

        @DisplayName("BLUE 궁성 내에서 대각선으로 이동 불가능한 위치에선 false를 반환한다.")
        @ParameterizedTest
        @MethodSource("invalidDiagonalMoveInRedPalace")
        void shouldReturnFalseWhereCantMoveDiagonalPositionInRedPalace(Position start, Position end) {
            // given
            Tank tank = new Tank(Side.BLUE);
            Map<Position, Piece> emptyInPalaceBoard = Map.of();

            // when
            boolean canMove = tank.canMove(start, end, emptyInPalaceBoard);

            // then
            assertThat(canMove).isFalse();
        }

        private static Stream<Arguments> invalidDiagonalMoveInBluePalace() {
            return Stream.of(
                    Arguments.of(new Position(4, 2), new Position(5, 1)),
                    Arguments.of(new Position(5, 1), new Position(6, 2)),
                    Arguments.of(new Position(6, 2), new Position(5, 3)),
                    Arguments.of(new Position(5, 3), new Position(4, 2))
            );
        }

        private static Stream<Arguments> invalidDiagonalMoveInRedPalace() {
            return Stream.of(
                    Arguments.of(new Position(4, 9), new Position(5, 8)),
                    Arguments.of(new Position(5, 8), new Position(6, 9)),
                    Arguments.of(new Position(6, 9), new Position(5, 10)),
                    Arguments.of(new Position(5, 10), new Position(4, 9))
            );
        }
    }
}
