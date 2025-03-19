package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MaTest {
    @Test
    @DisplayName("마 기물 생성 테스트")
    public void test1() {
        Team team = Team.RED;

        Ma ma = new Ma(team);

        assertThat(ma.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("마 이동 가능 여부 판별 테스트")
    class MaMovableTest {
        @Test
        @DisplayName("가능")
        public void test1() {
            Ma ma = new Ma(Team.RED);
            assertThat(ma.isValidPoint(Point.of(0,0), Point.of(2,1))).isTrue();
        }

        @Test
        @DisplayName("불가능")
        public void test2() {
            Ma ma = new Ma(Team.RED);
            assertThat(ma.isValidPoint(Point.of(0,0), Point.of(2,2))).isFalse();
        }
    }

    @Nested
    @DisplayName("마 이동 경로 계산 테스트")
    class MaCalculatePathTest {
        @Test
        @DisplayName("중간 경유지 포함 여부 테스트")
        public void test1() {
            Ma ma = new Ma(Team.RED);
            Point point1 = new Point(0, 1);
            Point point2 = new Point(1, 2);
            Path path = ma.calculatePath(Point.of(0,0), Point.of(1,2));

            assertAll(
                    () -> assertThat(path.contains(point1)).isTrue(),
                    () -> assertThat(path.contains(point2)).isTrue()
            );
        }
    }

    @Nested
    @DisplayName("마 경로 방해 테스트")
    class JangIsProhibitedPathTest {

        @Test
        @DisplayName("중간 아군")
        public void test1() {
            Ma ma = new Ma(Team.RED);
            Map<Piece,Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.RED),false);

            assertThat(ma.canMove(pieces)).isFalse();
        }

        @Test
        @DisplayName("종점 아군")
        public void test2() {
            Ma ma = new Ma(Team.RED);
            Map<Piece,Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.RED),true);

            assertThat(ma.canMove(pieces)).isFalse();
        }


        @Test
        @DisplayName("중간 적군")
        public void test3() {
            Ma ma = new Ma(Team.RED);
            Map<Piece,Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.BLUE),false);

            assertThat(ma.canMove(pieces)).isFalse();
        }

        @Test
        @DisplayName("종점 적군")
        public void test4() {
            Ma ma = new Ma(Team.RED);
            Map<Piece,Boolean> pieces = new HashMap<>();
            pieces.put(new Cha(Team.BLUE),true);

            assertThat(ma.canMove(pieces)).isTrue();
        }
    }
}
