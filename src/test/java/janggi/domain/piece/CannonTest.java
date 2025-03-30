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

class CannonTest {

    @Nested
    @DisplayName("일반 이동 테스트")
    class NormalMoveTest {

        @DisplayName("경로 상 말이 2개 이상인 경우 false를 반환한다.")
        @Test
        void shouldReturnFalseWhenExistsOverTwoPieceOnCannonPath() {
            // given
            Cannon cannon = new Cannon(Side.RED);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            Map<Position, Piece> overTwoPieceOnPathBoard = Map.of(
                    new Position(1, 1), cannon,
                    new Position(3, 1), new Soldier(Side.RED),
                    new Position(4, 1), new Soldier(Side.RED)
            );

            // when
            boolean canMove = cannon.canMove(start, end, overTwoPieceOnPathBoard);

            // then
            assertThat(canMove).isFalse();
        }

        @DisplayName("경로 상 말이 1개인 경우 true를 반환한다.")
        @Test
        void shouldReturnTrueWhenExistsOnePieceOnCannonPath() {
            // given
            Cannon cannon = new Cannon(Side.RED);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            Map<Position, Piece> onePieceOnPathBoard = Map.of(
                    new Position(1, 1), cannon,
                    new Position(3, 1), new Soldier(Side.RED)
            );

            // when
            boolean canMove = cannon.canMove(start, end, onePieceOnPathBoard);

            // then
            assertThat(canMove).isTrue();
        }

        @DisplayName("경로 상 포가 존재하는 경우 false를 반환한다.")
        @Test
        void shouldReturnFalseWhenExistsCannonOnCannonPath() {
            // given
            Cannon cannon = new Cannon(Side.RED);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            Map<Position, Piece> existsCannonOnPathBoard = Map.of(
                    new Position(1, 1), cannon,
                    new Position(3, 1), new Cannon(Side.RED)
            );

            // when
            boolean canMove = cannon.canMove(start, end, existsCannonOnPathBoard);

            // then
            assertThat(canMove).isFalse();
        }

        @DisplayName("포의 경로 상에 말이 존재하지 않는 경우 false를 반환한다.")
        @Test
        void shouldReturnFalseWhenEmptyOnCannonPath() {
            // given
            Cannon cannon = new Cannon(Side.RED);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            Map<Position, Piece> emptyOnPathBoard = Map.of(
                    new Position(1, 1), cannon
            );

            // when
            boolean canMove = cannon.canMove(start, end, emptyOnPathBoard);

            // then
            assertThat(canMove).isFalse();
        }

        @DisplayName("도착지의 말이 포인 경우 false를 반환한다.")
        @Test
        void shouldReturnFalseWhenCannonOnEndPosition() {
            // given
            Cannon cannon = new Cannon(Side.RED);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            Map<Position, Piece> cannonOnEndPositionBoard = Map.of(
                    new Position(1, 1), cannon,
                    new Position(3, 1), new Soldier(Side.RED),
                    new Position(6, 1), new Cannon(Side.BLUE)
            );

            // when
            boolean canMove = cannon.canMove(start, end, cannonOnEndPositionBoard);

            // then
            assertThat(canMove).isFalse();
        }

        @DisplayName("상하좌우로 이동하지 않으면 false를 발생한다.")
        @ParameterizedTest
        @CsvSource(value = {
                "6, 6",
                "4, 6",
                "6, 4",
                "4, 4"
        })
        void shouldReturnFalseWhenUnfollowMovingRule(int destX, int destY) {
            // given
            Cannon cannon = new Cannon(Side.RED);
            Position start = new Position(5, 5);
            Position end = new Position(destX, destY);

            // when
            boolean canMove = cannon.canMove(start, end, Map.of());

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
            Cannon cannon = new Cannon(Side.RED);
            Map<Position, Piece> existsPieceOnPathBoard = Map.of(new Position(5, 2), new King(Side.BLUE));

            // when
            boolean canMove = cannon.canMove(start, end, existsPieceOnPathBoard);

            // then
            assertThat(canMove).isTrue();
        }

        @DisplayName("RED 궁성 내에서 대각선으로 이동 가능하면 true를 반환한다.")
        @ParameterizedTest
        @MethodSource("validDiagonalMoveInRedPalace")
        void shouldReturnTrueWhenMoveDiagonalInRedPalace(Position start, Position end) {
            // given
            Cannon cannon = new Cannon(Side.BLUE);
            Map<Position, Piece> existsPieceOnPathBoard = Map.of(new Position(5, 9), new King(Side.RED));

            // when
            boolean canMove = cannon.canMove(start, end, existsPieceOnPathBoard);

            // then
            assertThat(canMove).isTrue();
        }

        @DisplayName("BLUE 궁성 내에서 대각선으로 이동 불가능한 위치에선 false를 반환한다.")
        @ParameterizedTest
        @MethodSource("invalidDiagonalMoveInBluePalace")
        void shouldReturnFalseWhereCantMoveDiagonalPositionInBluePalace(Position start, Position end) {
            // given
            Cannon cannon = new Cannon(Side.RED);
            Map<Position, Piece> existsPieceOnPathBoard = Map.of(new Position(5, 1), new King(Side.BLUE));

            // when
            boolean canMove = cannon.canMove(start, end, existsPieceOnPathBoard);

            // then
            assertThat(canMove).isFalse();
        }

        @DisplayName("BLUE 궁성 내에서 대각선으로 이동 불가능한 위치에선 false를 반환한다.")
        @ParameterizedTest
        @MethodSource("invalidDiagonalMoveInRedPalace")
        void shouldReturnFalseWhereCantMoveDiagonalPositionInRedPalace(Position start, Position end) {
            // given
            Cannon cannon = new Cannon(Side.BLUE);
            Map<Position, Piece> existsPieceOnPathBoard = Map.of(new Position(5, 9), new King(Side.RED));

            // when
            boolean canMove = cannon.canMove(start, end, existsPieceOnPathBoard);

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

        @DisplayName("BLUE 궁성 내에서 말이 없다면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource("validDiagonalMoveInBluePalace")
        void shouldReturnFalseWhenEmptyInRedPalace(Position start, Position end) {
            // given
            Cannon cannon = new Cannon(Side.RED);
            Map<Position, Piece> emptyOnPathBoard = Map.of();

            // when
            boolean canMove = cannon.canMove(start, end, emptyOnPathBoard);

            // then
            assertThat(canMove).isFalse();
        }

        @DisplayName("RED 궁성 내에서 말이 없다면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource("validDiagonalMoveInRedPalace")
        void shouldReturnFalseWhenEmptyRedPalace(Position start, Position end) {
            // given
            Cannon cannon = new Cannon(Side.BLUE);
            Map<Position, Piece> emptyOnPathBoard = Map.of();

            // when
            boolean canMove = cannon.canMove(start, end, emptyOnPathBoard);

            // then
            assertThat(canMove).isFalse();
        }

        private static Stream<Arguments> validDiagonalMoveInBluePalace() {
            return Stream.of(
                    Arguments.of(new Position(4, 1), new Position(6, 3)),
                    Arguments.of(new Position(6, 3), new Position(4, 1)),
                    Arguments.of(new Position(4, 3), new Position(6, 1)),
                    Arguments.of(new Position(6, 1), new Position(4, 3))
            );
        }

        private static Stream<Arguments> validDiagonalMoveInRedPalace() {
            return Stream.of(
                    Arguments.of(new Position(4, 8), new Position(6, 10)),
                    Arguments.of(new Position(6, 10), new Position(4, 8)),
                    Arguments.of(new Position(4, 10), new Position(6, 8)),
                    Arguments.of(new Position(6, 8), new Position(4, 10))
            );
        }
    }
}
