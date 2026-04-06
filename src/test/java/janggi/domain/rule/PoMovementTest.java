package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.support.TestPiece;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PoMovementTest {

    private static final PoMovement MOVEMENT = PoMovement.getInstance();

    @Nested
    class CalculateRouteTest {
        @ParameterizedTest
        @DisplayName("이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
        @MethodSource("provideReachableDestinationAndPathCoordination")
        void shouldReturnRouteForReachableLocation(Location destination, List<Location> path) {
            // given
            Location from = new Location(0, 0);

            // when & then
            Assertions.assertThat(MOVEMENT.calculateRoute(from, destination)).hasValue(path);
        }

        static Stream<Arguments> provideReachableDestinationAndPathCoordination() {
            return Stream.of(
                    Arguments.of(
                            new Location(-3,0), // 상
                            List.of(
                                    new Location(-1, 0),
                                    new Location(-2, 0),
                                    new Location(-3, 0)
                            )
                    ),
                    Arguments.of(
                            new Location(3,0), // 하
                            List.of(
                                    new Location(1, 0),
                                    new Location(2, 0),
                                    new Location(3, 0)
                            )
                    ),
                    Arguments.of(
                            new Location(0,-3), // 좌
                            List.of(
                                    new Location(0, -1),
                                    new Location(0, -2),
                                    new Location(0, -3)
                            )
                    ),
                    Arguments.of(
                            new Location(0,3), // 우
                            List.of(
                                    new Location(0, 1),
                                    new Location(0, 2),
                                    new Location(0, 3)
                            )
                    )
            );
        }

        @ParameterizedTest
        @DisplayName("이동할 수 없는 위치로 경로를 계산하면, 빈 결과를 반환한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldReturnEmptyForUnReachableLocation(Location destination) {
            // given
            Location from = new Location(0, 0);

            // when & then
            Assertions.assertThat(MOVEMENT.calculateRoute(from, destination)).isEmpty();
        }

        static List<Location> provideUnreachableCoordination() {
            return List.of(
                    new Location(-3,-3), // 왼쪽 대각선으로 전진
                    new Location(-3,3), // 오른쪽 대각선으로 전진
                    new Location(3,-3), // 왼쪽 대각선으로 후진
                    new Location(3,3) // 오른쪽 대각선으로 후진
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
        @DisplayName("이동 경로에 포가 아닌 장애물이 1개 존재하고, 도착 지점에 위치한 기물이 존재하지 않으면 예외를 반환하지 않는다.")
        void shouldNotThrowExceptionWhenOneNonPoObstacleAndNoPieceOnDestination() {
            // given
            List<Piece> piecesOnPath = List.of(EMPTY, new TestPiece(PieceType.CHA, Side.HAN), EMPTY);

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> MOVEMENT.detectCollision(Side.CHO, piecesOnPath));
        }

        @ParameterizedTest
        @DisplayName("이동 경로에 포가 아닌 장애물이 1개 존재하고, 도착 지점에 위치한 기물이 상대팀이면 예외를 반환하지 않는다.")
        @MethodSource("provideSide")
        void shouldNotThrowExceptionWhenOneNonPoObstacleAndPieceOnDestinationIsOtherSide(Side mySide) {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.MA, Side.HAN), EMPTY, new TestPiece(PieceType.CHA, mySide.switchSide()));

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> MOVEMENT.detectCollision(mySide, piecesOnPath));
        }

        @ParameterizedTest
        @DisplayName("이동 경로에 포가 아닌 장애물이 1개 존재하고, 도착 지점에 위치한 기물이 우리팀이면 예외를 발생시킨다.")
        @MethodSource("provideSide")
        void shouldThrowExceptionWhenOneNonPoObstacleAndPieceOnDestinationIsMySide(Side mySide) {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.MA, Side.HAN), EMPTY, new TestPiece(PieceType.CHA, mySide));

            // when & then
            Assertions.assertThatThrownBy(() -> MOVEMENT.detectCollision(mySide, piecesOnPath))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("이동 경로에 장애물이 2개 이상 존재하는 경우 예외를 발생시킨다.")
        void shouldThrowExceptionWhenTwoOrMorePieceOnPathExist() {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.PO, Side.CHO), new TestPiece(PieceType.MA, Side.CHO), EMPTY);

            // when & then
            Assertions.assertThatThrownBy(() -> MOVEMENT.detectCollision(Side.CHO, piecesOnPath))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("이동 경로에 장애물이 존재하지 않는 경우 예외를 발생시킨다.")
        void shouldThrowExceptionWhenNoObstacleOnPath() {
            // given
            List<Piece> piecesOnPath = List.of(EMPTY, EMPTY);

            // when & then
            Assertions.assertThatThrownBy(() -> MOVEMENT.detectCollision(Side.CHO, piecesOnPath))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("이동 경로에 포가 존재하는 경우 예외를 발생시킨다.")
        void shouldThrowExceptionWhenPoOnPath() {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.PO, Side.CHO), EMPTY, EMPTY);

            // when & then
            Assertions.assertThatThrownBy(() -> MOVEMENT.detectCollision(Side.HAN, piecesOnPath))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
