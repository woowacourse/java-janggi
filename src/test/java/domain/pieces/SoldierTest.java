package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class SoldierTest {

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void test_hasEqualTeam() {
        //given
        final Piece piece = new Soldier(Team.CHO);

        //when&then
        assertThat(piece.hasEqualTeam(Team.CHO)).isTrue();
        assertThat(piece.hasEqualTeam(Team.HAN)).isFalse();
    }

    @Nested
    @DisplayName("피스가 이동할 수 있는 지점들을 반환할 때")
    class TestAbleToArrive {

        @Test
        @DisplayName("한나라인 경우 북쪽으로 이동한 지점이 반환되지 않는다.")
        void test_isAbleToArriveByHan() {
            // given
            final Soldier soldier = new Soldier(Team.HAN);
            final Point startPoint = new Point(0, 0);
            final Point arrivalPoint = new Point(1, 0);

            // when
            final boolean actual = soldier.isAbleToArrive(startPoint, arrivalPoint);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("초나라인 경우 남쪽으로 이동한 지점이 반환되지 않는다.")
        void test_isAbleToArriveByCho() {
            // given
            final Soldier soldier = new Soldier(Team.CHO);
            final Point startPoint = new Point(0, 0);
            final Point arrivalPoint = new Point(-1, 0);

            // when
            final boolean actual = soldier.isAbleToArrive(startPoint, arrivalPoint);

            // then
            assertThat(actual).isFalse();
        }
    }

    @Test
    @DisplayName("경로에 있는 모든 지점들을 반환한다")
    void test_getRoutePoints() {
        // given
        final Soldier soldier = new Soldier(Team.CHO);
        final Point startPoint = new Point(0, 0);
        final Point arrivalPoint = new Point(1, 0);

        // when
        final List<Point> routePoints = soldier.getRoutePoints(startPoint, arrivalPoint);

        // then
        assertThat(routePoints).containsExactlyInAnyOrder(
                new Point(1, 0)
        );
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeamOnRoute() {
        //given
        final Soldier soldier = new Soldier(Team.CHO);
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null, soldier));

        //when&then
        assertThat(soldier.isMovableOnRoute(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 없으면 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeamOnRoute() {
        //given
        final Soldier soldierHan = new Soldier(Team.HAN);
        final Soldier soldierCho = new Soldier(Team.CHO);
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null, soldierCho));

        //when&then
        assertThat(soldierHan.isMovableOnRoute(piecesOnRoute)).isTrue();
    }

    @Test
    @DisplayName("졸은 팀에 따라 다르게 이름을 반환한다.")
    void test_toString() {
        //given
        final Piece pieceForCho = new Soldier(Team.CHO);
        final Piece pieceForHan = new Soldier(Team.HAN);

        //when&then
        assertThat(pieceForCho.getName()).isEqualTo("졸");
        assertThat(pieceForHan.getName()).isEqualTo("兵");
    }
}
