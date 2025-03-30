package domain.pieces;

import domain.Team;
import domain.board.BoardPoint;
import domain.board.PieceOnRoute;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GeneralTest {
    @Nested
    @DisplayName("장군의 도착 가능 여부를 확인할 때")
    class TestIsAbleToArrive {

        @Test
        @DisplayName("도착 지점이 궁 내부가 아니라면 false 를 반환한다.")
        void test_IsNotAbleToArriveOutOfPalace() {
            // given
            General general = new General(Team.HAN);
            BoardPoint startBoardPoint = new BoardPoint(0, 3);
            BoardPoint outOfPalace = new BoardPoint(0, 0);

            // when
            boolean ableToArrive = general.isAbleToArrive(startBoardPoint, outOfPalace);

            // then
            assertThat(ableToArrive).isFalse();
        }

        @Test
        @DisplayName("이동 가능한 위치라면 true 를 반환한다.")
        void test_IsAbleToArrive() {
            // given
            General general = new General(Team.HAN);
            BoardPoint startBoardPoint = new BoardPoint(0, 3);
            BoardPoint outOfPalace = new BoardPoint(1, 3);

            // when
            boolean ableToArrive = general.isAbleToArrive(startBoardPoint, outOfPalace);

            // then
            assertThat(ableToArrive).isTrue();
        }
    }

    @Nested
    @DisplayName("장군의 이동 경로를 확인할 때")
    class TestGetRoutePoints {

        @Test
        @DisplayName("한 칸만 이동 가능하므로 한 개만 반환된다.")
        void test_GetRoutePoints() {
            // given
            General general = new General(Team.HAN);
            BoardPoint startBoardPoint = new BoardPoint(0, 3);
            BoardPoint arrivalPoint = new BoardPoint(1, 3);

            // when
            List<BoardPoint> routePoints = general.getRoutePoints(startBoardPoint, arrivalPoint);

            // then
            assertThat(routePoints).hasSize(1);
        }
    }

    @Nested
    @DisplayName("장군의 이동 가능 여부를 확인할 때")
    class TestIsMovable {

        @Test
        @DisplayName("아군 기물이 있는 경우 이동할 수 없다.")
        void test_IsNotMovable() {
            // given
            General general = new General(Team.HAN);

            // when
            boolean isMovable = general.isMovable(new PieceOnRoute(List.of(), new Soldier(Team.HAN)));

            // then
            assertThat(isMovable).isFalse();
        }

        @Test
        @DisplayName("적군 기물이 있는 경우 이동할 수 있다.")
        void test_IsMovable() {
            // given
            General general = new General(Team.HAN);

            // when
            boolean isMovable = general.isMovable(new PieceOnRoute(List.of(), new Soldier(Team.CHO)));

            // then
            assertThat(isMovable).isTrue();
        }
    }
}
