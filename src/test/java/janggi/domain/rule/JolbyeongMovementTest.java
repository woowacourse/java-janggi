package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
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

class JolbyeongMovementTest {

    private static final JolbyeongMovement CHO_JOL_MOVEMENT = JolbyeongMovement.getInstanceBySide(Side.CHO);
    private static final JolbyeongMovement HAN_BYEONG_MOVEMENT = JolbyeongMovement.getInstanceBySide(Side.HAN);

    @Nested
    class CalculateRouteTest {
        @Test
        @DisplayName("한팀 졸병이 이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocationWhenTeamHan() {
            // given
            Location from = new Location(0, 0);
            Location to = new Location(1, 0);

            // when & then
            Assertions.assertThat(HAN_BYEONG_MOVEMENT.calculateRoute(from, to)).hasValue(List.of(to));
        }

        @Test
        @DisplayName("초팀 졸병이 이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocationWhenTeamCho() {
            // given
            Location from = new Location(1, 0);
            Location to = new Location(0, 0);

            // when & then
            Assertions.assertThat(CHO_JOL_MOVEMENT.calculateRoute(from, to)).hasValue(List.of(to));
        }

        @Test
        @DisplayName("한팀 졸병이 이동할 수 없는 위치로 경로를 계산하면, 빈 결과를 반환한다.")
        void shouldReturnEmptyForUnReachableLocationWhenTeamHan() {
            // given
            Location from = new Location(1, 0);
            Location to = new Location(0, 0);

            // when & then
            Assertions.assertThat(HAN_BYEONG_MOVEMENT.calculateRoute(from, to)).isEmpty();
        }

        @ParameterizedTest
        @DisplayName("초팀 졸병이 이동할 수 없는 위치로 경로를 계산하면, 빈 결과를 반환한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldReturnEmptyForUnReachableLocationWhenTeamCho(Location destination) {
            // given
            Location from = new Location(0, 0);

            // when & then
            Assertions.assertThat(CHO_JOL_MOVEMENT.calculateRoute(from, destination)).isEmpty();
        }

        static List<Location> provideUnreachableCoordination() {
            return List.of(
                    new Location(2,0), // 거리가 멀어서 도달할 수 없는 경우
                    new Location(1,1), // 대각선으로 이동하는 경우
                    new Location(1,0) // 뒤로 이동하는 경우
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
        @DisplayName("도착 지점에 위치한 기물이 존재하지 않으면 예외를 반환하지 않는다.")
        void shouldNotThrowExceptionWhenNoPieceOnDestination() {
            // given
            List<Piece> piecesOnPath = List.of(EMPTY);

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> CHO_JOL_MOVEMENT.detectCollision(Side.CHO, piecesOnPath));
            Assertions.assertThatNoException()
                    .isThrownBy(() -> HAN_BYEONG_MOVEMENT.detectCollision(Side.HAN, piecesOnPath));
        }

        @ParameterizedTest
        @DisplayName("도착 지점에 위치한 기물이 상대팀이면 예외를 반환하지 않는다.")
        @MethodSource("provideSide")
        void shouldNotThrowExceptionWhenPieceOnDestinationIsOtherSide(Side mySide) {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, mySide.switchSide()));
            JolbyeongMovement movement = JolbyeongMovement.getInstanceBySide(mySide);

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> movement.detectCollision(mySide, piecesOnPath));
        }

        @ParameterizedTest
        @DisplayName("도착 지점에 위치한 기물이 우리팀이면 예외를 발생시킨다.")
        @MethodSource("provideSide")
        void shouldThrowExceptionWhenPieceOnDestinationIsMySide(Side mySide) {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, mySide));
            JolbyeongMovement movement = JolbyeongMovement.getInstanceBySide(mySide);

            // when & then
            Assertions.assertThatThrownBy(() -> movement.detectCollision(mySide, piecesOnPath))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
