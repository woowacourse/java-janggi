package model.piece.goongsungpiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import model.Point;
import model.Team;
import model.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JangTest {

    @Test
    @DisplayName("장 기물 생성 테스트")
    void test1() {
        Team team = Team.RED;

        Jang jang = new Jang(team);

        assertThat(jang.getTeam()).isEqualTo(team);
    }

    @Test
    @DisplayName("사망 시 게임 종료 기물 테스트")
    void test2() {
        Piece jang = new Jang(Team.RED);
        assertThat(jang.isCriticalPiece()).isTrue();
    }

    @Nested
    @DisplayName("궁성 내부 이동 가능 위치 판별 테스트")
    class JangMovableTest {
        Jang jang = new Jang(Team.BLUE);
        Point blueGoongsungCenterPoint = Point.of(4, 1);

        @Test
        @DisplayName("수평 이동 1칸 가능")
        void test1() {
            assertThat(jang.isValidPoint(blueGoongsungCenterPoint, Point.of(5, 1))).isTrue();
        }

        @Test
        @DisplayName("수평 이동 2칸 불가능")
        void test2() {
            assertThat(jang.isValidPoint(Point.of(3, 1), Point.of(5, 1))).isFalse();
        }

        @Test
        @DisplayName("수직 이동 1칸 가능")
        void test3() {
            assertThat(jang.isValidPoint(blueGoongsungCenterPoint, Point.of(4, 2))).isTrue();
        }

        @Test
        @DisplayName("수직 이동 2칸 불가능")
        void test4() {
            assertThat(jang.isValidPoint(Point.of(4, 0), Point.of(4, 2))).isFalse();
        }

        @Test
        @DisplayName("궁성 외부로 이동불가")
        void test5() {
            assertThatThrownBy(() -> jang.isValidPoint(Point.of(4, 2), Point.of(4, 3))).hasMessage(
                    "[ERROR] 궁성을 벗어날 수 없습니다.");
        }

        @Nested
        @DisplayName("대각선 이동")
        class JangDiagonalMovableTest {
            // 4 1
            @Test
            @DisplayName("중점에서 대각 위")
            void test1() {
                assertThat(jang.isValidPoint(blueGoongsungCenterPoint, Point.of(5, 2))).isTrue();
            }

            @Test
            @DisplayName("중점에서 대각 아래")
            void test2() {
                assertThat(jang.isValidPoint(blueGoongsungCenterPoint, Point.of(5, 0))).isTrue();
            }

            @Test
            @DisplayName("대각 위로 중점")
            void test3() {
                assertThat(jang.isValidPoint(Point.of(3, 0), blueGoongsungCenterPoint)).isTrue();
            }

            @Test
            @DisplayName("대각 아래로 중점")
            void test4() {
                assertThat(jang.isValidPoint(Point.of(3, 2), blueGoongsungCenterPoint)).isTrue();
            }

            @Test
            @DisplayName("중점을 경유하지 않는 대각선(간선 없음)")
            void test5() {
                assertThat(jang.isValidPoint(Point.of(3, 1), Point.of(4, 2))).isFalse();
            }

            @Test
            @DisplayName("중점을 경유하지 않는 대각선(간선 없음) - 2")
            void test6() {
                assertThat(jang.isValidPoint(Point.of(4, 0), Point.of(5, 1))).isFalse();
            }

        }

    }

    @Nested
    @DisplayName("장 이동 경로 계산 테스트")
    class JangCalculatePathTest {
        @Test
        @DisplayName("수직")
        void test1() {
            Jang jang = new Jang(Team.RED);
            Point point = new Point(0, 1);

            assertThat(jang.calculatePath(Point.of(0, 0), Point.of(0, 1)).contains(point)).isTrue();
        }

        @Test
        @DisplayName("수평")
        void test2() {
            Jang jang = new Jang(Team.RED);
            Point point = new Point(1, 0);

            assertThat(jang.calculatePath(Point.of(0, 0), Point.of(1, 0)).contains(point)).isTrue();
        }
    }

    @Nested
    @DisplayName("장 경로 방해 테스트")
    class JangIsProhibitedPathTest {

        @Test
        @DisplayName("아군")
        void test1() {
            Jang jang = new Jang(Team.RED);
            Map<Piece, Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.RED), true);

            assertThat(jang.canMove(pieces)).isFalse();
        }

        @Test
        @DisplayName("적군")
        void test2() {
            Jang jang = new Jang(Team.RED);
            Map<Piece, Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.BLUE), true);
            assertThat(jang.canMove(pieces)).isTrue();
        }
    }

}
