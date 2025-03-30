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

public final class ChariotTest {

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void test_hasEqualTeam() {
        //given
        final Piece piece = new Chariot(new Player(1, Team.CHO));

        //when&then
        assertThat(piece.hasEqualTeam(Team.CHO)).isTrue();
        assertThat(piece.hasEqualTeam(Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("도착할 수 있는지 확인한다.")
    void test_IsAbleToArrive() {
        // given
        final Chariot chariot = new Chariot(new Player(1, Team.CHO));
        final Point startPoint = new Point(0, 0);
        final Point arrivalPoint = new Point(0, 9);

        // when
        boolean actual = chariot.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("도착할 수 없는지 확인한다.")
    void test_IsNotAbleToArrive() {
        // given
        final Chariot chariot = new Chariot(new Player(1, Team.CHO));
        final Point startPoint = new Point(0, 0);
        final Point arrivalPoint = new Point(3, 3);

        // when
        boolean actual = chariot.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isFalse();
    }


    @Test
    @DisplayName("도착 위치까지의 경로를 모두 반환한다.")
    void test_searchRoutePoints() {
        // given
        final Chariot chariot = new Chariot(new Player(1, Team.CHO));
        final Point startPoint = new Point(0, 0);
        final Point arrivalPoint = new Point(0, 3);

        // when
        List<Point> routePoints = chariot.searchRoutePoints(startPoint, arrivalPoint);

        // then
        assertThat(routePoints).containsExactlyInAnyOrder(
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        );
    }

    @Test
    @DisplayName("경로 상 기물이 있으면 이동할 수 없다.")
    void test_isMovableOnRouteWhenPieceOnRoute() {
        //given
        final Chariot chariot = new Chariot(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(chariot, null, null));

        //when&then
        assertThat(chariot.isMovableOnRoute(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 있다.")
    void test_isMovableOnRoute() {
        //given
        final Chariot chariot = new Chariot(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null, null));

        //when&then
        assertThat(chariot.isMovableOnRoute(piecesOnRoute)).isTrue();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있을 경우, 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeamOnRoute() {
        //given
        final Chariot chariot = new Chariot(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null, chariot));

        //when&then
        assertThat(chariot.isMovableOnRoute(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 적군 기물이 있을 경우, 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeamOnRoute() {
        //given
        final Chariot chariotHan = new Chariot(new Player(1, Team.HAN));
        final Chariot chariotCho = new Chariot(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null, chariotCho));

        //when&then
        assertThat(chariotHan.isMovableOnRoute(piecesOnRoute)).isTrue();
    }

    @Test
    @DisplayName("차는 팀에 따라 다르게 이름을 반환한다.")
    void test_toString() {
        //given
        final Piece pieceForCho = new Chariot(new Player(1, Team.CHO));
        final Piece pieceForHan = new Chariot(new Player(1, Team.HAN));

        //when&then
        assertThat(pieceForCho.getName()).isEqualTo("차");
        assertThat(pieceForHan.getName()).isEqualTo("車");
    }
}
