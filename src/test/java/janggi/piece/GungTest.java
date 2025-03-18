package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.TeamColor;
import janggi.point.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GungTest {

    @Nested
    @DisplayName("궁 말 초기화 테스트")
    class InitPieceTest {

        @Test
        @DisplayName("팀당 한 개의 말을 생성할 수 있다.")
        void createOnePiecePerTeam() {
            List<Gung> gungs = Gung.values();

            assertThat(gungs).hasSize(2);
        }
    }

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Gung gung = new Gung(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 5);

            assertThat(gung.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Gung gung = new Gung(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 7);

            assertThat(gung.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Gung gung = new Gung(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(5, 6);

            assertThat(gung.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Gung gung = new Gung(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(7, 6);

            assertThat(gung.isMovable(targetPoint)).isTrue();
        }
    }
}
