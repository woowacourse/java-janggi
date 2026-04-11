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

class ByeongMovementTest {

    private static final ByeongMovement MOVEMENT = ByeongMovement.create(GungSeong.of(10, 9));

    @Nested
    class CalculateRouteTest {

        @Nested
        class DefaultRouteTest {

            @Test
            @DisplayName("이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
            void shouldReturnRouteForReachableLocation() {
                // given
                Location from = new Location(0, 0);
                Location to = new Location(1, 0);

                // when & then
                Assertions.assertThat(MOVEMENT.calculateRoute(from, to)).hasValue(List.of(to));
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
                        new Location(2,0), // 거리가 멀어서 도달할 수 없는 경우
                        new Location(1,1), // 대각선으로 이동하는 경우
                        new Location(-1,0) // 뒤로 이동하는 경우
                );
            }
        }

        @Nested
        class GungSeongRouteTest {

            @ParameterizedTest
            @DisplayName("궁성 영역에서 이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
            @MethodSource("provideMovableLocationsInGungSeongArea")
            void shouldReturnRouteForReachableLocation(Location from, Location to) {
                // when & then
                Assertions.assertThat(MOVEMENT.calculateRoute(from, to)).hasValue(List.of(to));
            }

            static Stream<Arguments> provideMovableLocationsInGungSeongArea() {
                return Stream.of(
                        Arguments.of(
                                new Location(0, 3),
                                new Location(1, 4)
                        ),
                        Arguments.of(
                                new Location(1, 4),
                                new Location(2, 3)
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
                        Arguments.of(
                                new Location(1, 4),
                                new Location(0, 3)
                        ),
                        Arguments.of(
                                new Location(1, 4),
                                new Location(0, 5)
                        )
                );
            }
        }
    }

    @Nested
    class DetectCollisionTest {

        private static final Piece EMPTY = EmptyPiece.getInstance();
        private static final Side MY_SIDE = Side.HAN;

        @Test
        @DisplayName("도착 지점에 위치한 기물이 존재하지 않으면 예외를 반환하지 않는다.")
        void shouldNotThrowExceptionWhenNoPieceOnDestination() {
            // given
            List<Piece> piecesOnPath = List.of(EMPTY);

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> MOVEMENT.detectCollision(MY_SIDE, piecesOnPath));
        }

        @Test
        @DisplayName("도착 지점에 위치한 기물이 상대팀이면 예외를 반환하지 않는다.")
        void shouldNotThrowExceptionWhenPieceOnDestinationIsOtherSide() {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, MY_SIDE.switchSide()));

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> MOVEMENT.detectCollision(MY_SIDE, piecesOnPath));
        }

        @Test
        @DisplayName("도착 지점에 위치한 기물이 우리팀이면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenPieceOnDestinationIsMySide() {
            // given
            List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, MY_SIDE));

            // when & then
            Assertions.assertThatThrownBy(() -> MOVEMENT.detectCollision(MY_SIDE, piecesOnPath))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
