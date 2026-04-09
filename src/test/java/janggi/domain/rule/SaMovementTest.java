package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.GungSeong;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.support.TestPiece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SaMovementTest {

    private static final SaMovement MOVEMENT = SaMovement.create(GungSeong.of(10, 9));

    @Nested
    class CalculateRouteTest {
        @ParameterizedTest
        @DisplayName("이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
        @MethodSource("provideReachableCoordination")
        void shouldReturnRouteForReachableLocation(Location to) {
            // given
            Location from = new Location(1, 4);

            // when & then
            Assertions.assertThat(MOVEMENT.calculateRoute(from, to)).hasValue(List.of(to));
        }

        static List<Location> provideReachableCoordination() {
            return List.of(
                    new Location(0, 4), // 상
                    new Location(2, 4), // 하
                    new Location(1, 3), // 좌
                    new Location(1, 5), // 우
                    new Location(0, 3), // 좌대각 전진
                    new Location(0, 5), // 우대각 전진
                    new Location(2, 3), // 좌대각 후진
                    new Location(2, 5) // 우대각 후진
            );
        }

        @ParameterizedTest
        @DisplayName("이동할 수 없는 위치로 경로를 계산하면, 빈 결과를 반환한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldReturnEmptyForUnReachableLocation(Location destination) {
            // given
            Location from = new Location(2, 3);

            // when & then
            Assertions.assertThat(MOVEMENT.calculateRoute(from, destination)).isEmpty();
        }

        static List<Location> provideUnreachableCoordination() {
            return List.of(
                    new Location(1, 2),
                    new Location(2, 2),
                    new Location(3, 2),
                    new Location(3, 3),
                    new Location(3, 4)
            );
        }
    }

    @Nested
    class DetectCollisionTest {
        private static final Piece EMPTY = EmptyPiece.getInstance();

        static List<Side> provideSide() {
            return List.of(Side.HAN, Side.CHO);
        }

        @Test
        @DisplayName("도착 지점에 기물이 존재하지 않으면 예외를 반환하지 않는다.")
        void shouldNotThrowExceptionWhenNoPieceOnPathAndNoPieceOnDestination() {
            // given
            List<Piece> piecesOnPath = List.of(EMPTY);

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> MOVEMENT.detectCollision(Side.CHO, piecesOnPath));
        }

        @ParameterizedTest
        @DisplayName("도착 지점에 위치한 기물이 상대팀이면 예외를 반환하지 않는다.")
        @MethodSource("provideSide")
        void shouldNotThrowExceptionWhenNoPieceOnPathAndPieceOnDestinationIsOtherSide(Side mySide) {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, mySide.switchSide()));

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> MOVEMENT.detectCollision(mySide, piecesOnPath));
        }

        @ParameterizedTest
        @DisplayName("도착 지점에 위치한 기물이 우리팀이면 예외를 발생시킨다.")
        @MethodSource("provideSide")
        void shouldThrowExceptionWhenNoPieceOnPathAndPieceOnDestinationIsMySide(Side mySide) {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, mySide));

            // when & then
            Assertions.assertThatThrownBy(() -> MOVEMENT.detectCollision(mySide, piecesOnPath))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
