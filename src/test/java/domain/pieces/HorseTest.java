package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.player.Player;
import domain.player.Team;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class HorseTest {

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void test_hasEqualTeam() {
        //given
        final Piece piece = new Horse(new Player(1, Team.CHO));

        //when&then
        assertThat(piece.hasEqualTeam(Team.CHO)).isTrue();
        assertThat(piece.hasEqualTeam(Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("피스가 이동할 수 있는 지점들을 전부 반환한다")
    void test_isAbleToArrive() {
        // given
        final Horse horse = new Horse(new Player(1, Team.CHO));
        final Point startPoint = new Point(0, 0);
        final Point arrivalPoint = new Point(2, 1);

        // when
        boolean actual = horse.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("경로에 있는 모든 지점들을 반환한다")
    void test_searchRoutePoints() {
        // given
        final Horse horse = new Horse(new Player(1, Team.CHO));
        final Point startPoint = new Point(0, 0);
        final Point arrivalPoint = new Point(2, 1);

        // when
        final List<Point> routePoints = horse.searchRoutePoints(startPoint, arrivalPoint);

        // then
        assertThat(routePoints).containsExactlyInAnyOrder(
                new Point(1, 0),
                new Point(2, 1)
        );
    }

    @Test
    @DisplayName("경로 상 기물이 있으면 이동할 수 없다.")
    void test_isMovableOnRouteWhenPieceOnRoute() {
        //given
        final Horse horse = new Horse(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(horse, null));

        //when&then
        assertThat(horse.isMovableOnRoute(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 있다.")
    void test_isMovableOnRoute() {
        //given
        final Horse horse = new Horse(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null));

        //when&then
        assertThat(horse.isMovableOnRoute(piecesOnRoute)).isTrue();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeamOnRoute() {
        //given
        final Horse horse = new Horse(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, horse));

        //when&then
        assertThat(horse.isMovableOnRoute(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 적군 기물이 있을 경우, 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeamOnRoute() {
        //given
        final Horse horseHan = new Horse(new Player(1, Team.HAN));
        final Horse horseCho = new Horse(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, horseCho));

        //when&then
        assertThat(horseHan.isMovableOnRoute(piecesOnRoute)).isTrue();
    }

    @Test
    @DisplayName("마는 팀에 따라 다르게 이름을 반환한다.")
    void test_toString() {
        //given
        final Piece pieceForCho = new Horse(new Player(1, Team.CHO));
        final Piece pieceForHan = new Horse(new Player(1, Team.HAN));

        //when&then
        assertThat(pieceForCho.getName()).isEqualTo("마");
        assertThat(pieceForHan.getName()).isEqualTo("馬");
    }
}
