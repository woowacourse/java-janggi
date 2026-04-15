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

class MaMovementTest {

    private static final MaMovement MOVEMENT = MaMovement.getInstance();

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
                            new Location(-2, -1), // 상 + 좌측 대각선 전진
                            List.of(
                                    new Location(-1, 0),
                                    new Location(-2, -1)
                            )
                    ),
                    Arguments.of(
                            new Location(-2, 1), // 상 + 우측 대각선 전진
                            List.of(
                                    new Location(-1, 0),
                                    new Location(-2, 1)
                            )
                    ),
                    Arguments.of(
                            new Location(2, -1), // 하 + 좌측 대각선 후진
                            List.of(
                                    new Location(1, 0),
                                    new Location(2, -1)
                            )
                    ),
                    Arguments.of(
                            new Location(2, 1), // 하 + 우측 대각선 후진
                            List.of(
                                    new Location(1, 0),
                                    new Location(2, 1)
                            )
                    ),
                    Arguments.of(
                            new Location(-1, -2), // 좌 + 좌측 대각선 전진
                            List.of(
                                    new Location(0, -1),
                                    new Location(-1, -2)
                            )
                    ),
                    Arguments.of(
                            new Location(1, -2), // 좌 + 좌측 대각선 후진
                            List.of(
                                    new Location(0, -1),
                                    new Location(1, -2)
                            )
                    ),
                    Arguments.of(
                            new Location(-1, 2), // 우 + 우측 대각선 전진
                            List.of(
                                    new Location(0, 1),
                                    new Location(-1, 2)
                            )
                    ),
                    Arguments.of(
                            new Location(1, 2), // 우 + 우측 대각선 후진
                            List.of(
                                    new Location(0, 1),
                                    new Location(1, 2)
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
                    new Location(-2,0), // 상
                    new Location(2,0), // 하
                    new Location(0,-2), // 좌
                    new Location(0,2) // 우
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
        @DisplayName("이동 경로에 장애물이 존재하지 않고, 도착 지점에 위치한 기물이 존재하지 않으면 예외를 반환하지 않는다.")
        void shouldNotThrowExceptionWhenNoPieceOnPathAndNoPieceOnDestination() {
            // given
            List<Piece> piecesOnPath = List.of(EMPTY, EMPTY, EMPTY);

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> MOVEMENT.detectCollision(Side.CHO, piecesOnPath));
        }

        @ParameterizedTest
        @DisplayName("이동 경로에 장애물이 존재하지 않고, 도착 지점에 위치한 기물이 상대팀이면 예외를 반환하지 않는다.")
        @MethodSource("provideSide")
        void shouldNotThrowExceptionWhenNoPieceOnPathAndPieceOnDestinationIsOtherSide(Side mySide) {
            // given
            List<Piece> piecesOnPath = List.of(EMPTY, EMPTY, new TestPiece(PieceType.CHA, mySide.switchSide()));

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> MOVEMENT.detectCollision(mySide, piecesOnPath));
        }

        @ParameterizedTest
        @DisplayName("이동 경로에 장애물이 존재하지 않고, 도착 지점에 위치한 기물이 우리팀이면 예외를 발생시킨다.")
        @MethodSource("provideSide")
        void shouldThrowExceptionWhenNoPieceOnPathAndPieceOnDestinationIsMySide(Side mySide) {
            // given
            List<Piece> piecesOnPath = List.of(EMPTY, EMPTY, new TestPiece(PieceType.CHA, mySide));

            // when & then
            Assertions.assertThatThrownBy(() -> MOVEMENT.detectCollision(mySide, piecesOnPath))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("이동 경로에 장애물이 존재하면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenPieceOnPath() {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, Side.CHO), EMPTY, EMPTY);

            // when & then
            Assertions.assertThatThrownBy(() -> MOVEMENT.detectCollision(Side.HAN, piecesOnPath))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
