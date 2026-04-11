package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.GungSeong;
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

    private static final PoMovement MOVEMENT = PoMovement.create(GungSeong.of(10, 9));

    @Nested
    class CalculateRouteTest {

        @Nested
        class DefaultRouteTest {

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
        class GungSeongRouteTest {

            @ParameterizedTest
            @DisplayName("궁성 영역에서 이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
            @MethodSource("provideMovableLocationsInGungSeongArea")
            void shouldReturnRouteForReachableLocation(Location from, Location to, List<Location> validPath) {
                // when & then
                Assertions.assertThat(MOVEMENT.calculateRoute(from, to)).hasValue(validPath);
            }

            static Stream<Arguments> provideMovableLocationsInGungSeongArea() {
                return Stream.of(
                        // 한 진영
                        Arguments.of(
                                new Location(0, 3),
                                new Location(1, 4),
                                List.of(new Location(1, 4))
                        ),
                        Arguments.of(
                                new Location(0, 3),
                                new Location(2, 5),
                                List.of(new Location(1, 4), new Location(2, 5))
                        ),

                        // 초 진영
                        Arguments.of(
                                new Location(9, 5),
                                new Location(8, 4),
                                List.of(new Location(8, 4))
                        ),
                        Arguments.of(
                                new Location(9, 5),
                                new Location(7, 3),
                                List.of(new Location(8, 4), new Location(7, 3))
                        )
                );
            }

            @ParameterizedTest
            @DisplayName("궁성 영역에서 이동할 수 없는 경로로 계산을 시도하면, 빈 결과를 반환한다.")
            @MethodSource("provideImmovableCoordination")
            void shouldReturnEmptyForImmovableLocation(Location from, Location to) {
                // when & then
                Assertions.assertThat(MOVEMENT.calculateRoute(from, to)).isEmpty();
            }

            static Stream<Arguments> provideImmovableCoordination() {
                return Stream.of(
                        // 한 진영
                        Arguments.of(
                                new Location(0, 4),
                                new Location(1, 3)
                        ),
                        Arguments.of(
                                new Location(0, 4),
                                new Location(1, 5)
                        ),

                        // 초 진영
                        Arguments.of(
                                new Location(7, 4),
                                new Location(8, 3)
                        ),
                        Arguments.of(
                                new Location(7, 4),
                                new Location(8, 5)
                        )
                );
            }
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
