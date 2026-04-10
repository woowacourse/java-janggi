package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Side 테스트")
class SideTest {

    @DisplayName("NONE 진영(널 객체) 테스트")
    @Nested
    class NoneSideTest {

        @DisplayName("다음 턴을 요청해도 여전히 NONE이다")
        @Test
        void 다음_턴도_NONE이다() {
            Side none = Side.NONE;
            assertThat(none.nextTurn()).isEqualTo(Side.NONE);
        }

        @DisplayName("이동을 요청하면 이동하지 않는다(제자리 이동)")
        @Test
        void 이동_요청_시_제자리() {
            Side none = Side.NONE;
            Intersection startIntersection = new Intersection(5, 5);
            MoveAmount moveAmount = new MoveAmount(1);

            List<Intersection> movedIntersections = List.of(
                    none.moveForward(startIntersection, moveAmount),
                    none.moveLeft(startIntersection, moveAmount),
                    none.moveRight(startIntersection, moveAmount),
                    none.moveBackward(startIntersection, moveAmount),
                    none.moveForwardLeft(startIntersection, moveAmount),
                    none.moveForwardRight(startIntersection, moveAmount)
            );

            for (Intersection movedIntersection : movedIntersections) {
                assertThat(movedIntersection).isEqualTo(startIntersection);
            }
        }

        @DisplayName("기준 행/열 계산 시 NPE가 발생하지 않는다")
        @Test
        void 기준_좌표_계산_NPE_미발생() {
            Side none = Side.NONE;
            MoveAmount amount = new MoveAmount(0);

            assertThatNoException().isThrownBy(() -> {
                none.getRowAt(amount);
                none.getFileAt(amount);
            });
        }
    }

    @DisplayName("초 진영 테스트")
    @Nested
    class 초_진영_테스트 {

        private final Side cho = Side.CHO;

        @DisplayName("다음 턴을 요청하면 한 진영이다")
        @Test
        void 다음_턴은_HAN이다() {
            assertThat(cho.nextTurn()).isEqualTo(Side.HAN);
        }

        @DisplayName("기준 위치로부터의 상대 위치 계산 검증")
        @Test
        void 상대_위치_계산_검증() {
            assertThat(cho.getRowAt(new MoveAmount(3))).isEqualTo(7);
            assertThat(cho.getFileAt(new MoveAmount(1))).isEqualTo(2);
        }

        @DisplayName("각 방향별 이동 시의 좌표 변화를 검증")
        @Nested
        class 이동시_좌표_검증 {

            private final Intersection startIntersection = new Intersection(5, 5);
            private final MoveAmount amount = new MoveAmount(1);

            @DisplayName("전진")
            @Test
            void 전진() {
                assertThat(cho.moveForward(startIntersection, amount))
                        .isEqualTo(new Intersection(4, 5));
            }

            @DisplayName("후진")
            @Test
            void 후진() {
                assertThat(cho.moveBackward(startIntersection, amount))
                        .isEqualTo(new Intersection(6, 5));
            }

            @DisplayName("왼쪽")
            @Test
            void 왼쪽() {
                assertThat(cho.moveLeft(startIntersection, amount))
                        .isEqualTo(new Intersection(5, 4));
            }

            @DisplayName("오른쪽")
            @Test
            void 오른쪽() {
                assertThat(cho.moveRight(startIntersection, amount))
                        .isEqualTo(new Intersection(5, 6));
            }

            @DisplayName("전진 왼쪽 대각선")
            @Test
            void 전진_왼쪽_대각선() {
                assertThat(cho.moveForwardLeft(startIntersection, amount))
                        .isEqualTo(new Intersection(4, 4));
            }

            @DisplayName("전진_오른쪽 대각선")
            @Test
            void 전진_오른쪽_대각선() {
                assertThat(cho.moveForwardRight(startIntersection, amount))
                        .isEqualTo(new Intersection(4, 6));
            }

            @DisplayName("후진 왼쪽 대각선")
            @Test
            void 후진_왼쪽_대각선() {
                assertThat(cho.moveBackwardLeft(startIntersection, amount))
                        .isEqualTo(new Intersection(6, 4));
            }

            @DisplayName("후진_오른쪽_대각선")
            @Test
            void 후진_오른쪽_대각선() {
                assertThat(cho.moveBackwardRight(startIntersection, amount))
                        .isEqualTo(new Intersection(6, 6));
            }
        }
    }

    @DisplayName("한 진영 테스트")
    @Nested
    class 한_진영_테스트 {

        private final Side han = Side.HAN;

        @DisplayName("다음 턴을 요청하면 초 진영이다")
        @Test
        void 다음_턴은_CHO이다() {
            assertThat(han.nextTurn()).isEqualTo(Side.CHO);
        }

        @DisplayName("기준 위치로부터의 상대 위치 계산 검증")
        @Test
        void 상대_위치_계산_검증() {
            assertThat(han.getRowAt(new MoveAmount(3))).isEqualTo(4);
            assertThat(han.getFileAt(new MoveAmount(1))).isEqualTo(8);
        }

        @DisplayName("각 방향별 이동 시의 좌표 변화 검증")
        @Nested
        class 이동시_좌표_검증 {

            private final Intersection startIntersection = new Intersection(5, 5);
            private final MoveAmount amount = new MoveAmount(1);

            @DisplayName("전진")
            @Test
            void 전진() {
                assertThat(han.moveForward(startIntersection, amount))
                        .isEqualTo(new Intersection(6, 5));
            }

            @DisplayName("후진")
            @Test
            void 후진() {
                assertThat(han.moveBackward(startIntersection, amount))
                        .isEqualTo(new Intersection(4, 5));
            }

            @DisplayName("왼쪽")
            @Test
            void 왼쪽() {
                assertThat(han.moveLeft(startIntersection, amount))
                        .isEqualTo(new Intersection(5, 6));
            }

            @DisplayName("오른쪽")
            @Test
            void 오른쪽() {
                assertThat(han.moveRight(startIntersection, amount))
                        .isEqualTo(new Intersection(5, 4));
            }

            @DisplayName("전진 왼쪽 대각선")
            @Test
            void 전진_왼쪽_대각선() {
                assertThat(han.moveForwardLeft(startIntersection, amount))
                        .isEqualTo(new Intersection(6, 6));
            }

            @DisplayName("전진_오른쪽 대각선")
            @Test
            void 전진_오른쪽_대각선() {
                assertThat(han.moveForwardRight(startIntersection, amount))
                        .isEqualTo(new Intersection(6, 4));
            }

            @DisplayName("후진 왼쪽 대각선")
            @Test
            void 후진_왼쪽_대각선() {
                assertThat(han.moveBackwardLeft(startIntersection, amount))
                        .isEqualTo(new Intersection(4, 6));
            }

            @DisplayName("후진_오른쪽_대각선")
            @Test
            void 후진_오른쪽_대각선() {
                assertThat(han.moveBackwardRight(startIntersection, amount))
                        .isEqualTo(new Intersection(4, 4));
            }
        }
    }
}
