package model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import model.Point;
import model.Team;
import model.piece.goongsungpiece.Byeong;
import model.piece.goongsungpiece.Cha;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ByeongTest {
    @Test
    @DisplayName("병 기물 생성 테스트")
    public void test1() {
        Team team = Team.RED;

        Byeong byeong = new Byeong(team);

        assertThat(byeong.getTeam()).isEqualTo(team);
    }

    @Test
    @DisplayName("사망 시 게임 종료 기물 테스트")
    void test2() {
        Piece byeong = new Byeong(Team.RED);
        assertThat(byeong.isCriticalPiece()).isFalse();
    }

    @Nested
    @DisplayName("병 이동 가능 여부 판별 테스트")
    class ByeongMovableTest {
        @Test
        @DisplayName("초나라 병 이동 가능 테스트")
        public void test1() {
            Byeong byeong = new Byeong(Team.BLUE);
            assertAll(
                    () -> assertThat(byeong.isValidPoint(Point.of(0, 0), Point.of(0, 1))).isTrue(),
                    () -> assertThat(byeong.isValidPoint(Point.of(0, 0), Point.of(0, -1))).isFalse()
            );
        }

        @Test
        @DisplayName("초나라 병 궁성 이동 테스트")
        public void test2() {
            Byeong byeong = new Byeong(Team.BLUE);
            assertAll(
                    () -> assertThat(byeong.isValidPoint(Point.of(3, 7), Point.of(4, 8))).isTrue(),
                    () -> assertThat(byeong.isValidPoint(Point.of(4, 8), Point.of(3, 7))).isFalse(),
                    () -> assertThat(byeong.isValidPoint(Point.of(3, 8), Point.of(4, 9))).isFalse()
            );
        }

        @Test
        @DisplayName("한나라 병 이동 가능 테스트")
        public void test3() {
            Byeong byeong = new Byeong(Team.RED);
            assertAll(
                    () -> assertThat(byeong.isValidPoint(Point.of(0, 0), Point.of(0, 1))).isFalse(),
                    () -> assertThat(byeong.isValidPoint(Point.of(0, 0), Point.of(0, -1))).isTrue()
            );
        }

        @Test
        @DisplayName("한나라 병 궁성 이동 테스트")
        public void test4() {
            Byeong byeong = new Byeong(Team.RED);
            assertAll(
                    () -> assertThat(byeong.isValidPoint(Point.of(3, 2), Point.of(4, 1))).isTrue(),
                    () -> assertThat(byeong.isValidPoint(Point.of(4, 1), Point.of(3, 2))).isFalse(),
                    () -> assertThat(byeong.isValidPoint(Point.of(4, 2), Point.of(5, 1))).isFalse()
            );
        }

        @Test
        @DisplayName("병 이동 불가능 테스트")
        public void test5() {
            Byeong byeong = new Byeong(Team.RED);
            assertThat(byeong.isValidPoint(Point.of(0, 0), Point.of(2, 0))).isFalse();
        }
    }

    @Nested
    @DisplayName("병 이동 경로 계산 테스트")
    class ByeongCalculatePathTest {
        @Test
        @DisplayName("수직")
        public void test1() {
            Byeong byeong = new Byeong(Team.RED);
            Point point = new Point(0, 1);

            assertThat(byeong.calculatePath(Point.of(0, 0), Point.of(0, 1)).contains(point)).isTrue();
        }

        @Test
        @DisplayName("수평")
        public void test2() {
            Byeong byeong = new Byeong(Team.RED);
            Point point = new Point(1, 0);

            assertThat(byeong.calculatePath(Point.of(0, 0), Point.of(1, 0)).contains(point)).isTrue();
        }
    }

    @Nested
    @DisplayName("병 경로 방해 테스트")
    class ByeongIsProhibitedPathTest {

        @Test
        @DisplayName("아군")
        public void test1() {
            Byeong byeong = new Byeong(Team.RED);
            Map<Piece, Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.RED), true);

            assertThat(byeong.canMove(pieces)).isFalse();
        }

        @Test
        @DisplayName("적군")
        public void test2() {
            Byeong byeong = new Byeong(Team.RED);
            Map<Piece, Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.BLUE), true);
            assertThat(byeong.canMove(pieces)).isTrue();
        }
    }
}
