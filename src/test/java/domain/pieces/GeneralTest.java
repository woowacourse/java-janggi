package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.player.Player;
import domain.player.Team;
import exceptions.JanggiGameRuleWarningException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class GeneralTest {

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void test_hasEqualTeam() {
        //given
        final Piece piece = new General(new Player(1, Team.CHO));

        //when&then
        assertThat(piece.hasEqualTeam(Team.CHO)).isTrue();
        assertThat(piece.hasEqualTeam(Team.HAN)).isFalse();
    }


    @Test
    @DisplayName("경로에 있는 모든 지점들을 반환한다")
    void test_searchRoutePoints() {
        // given
        final Piece general = new General(new Player(1, Team.CHO));
        final Point startPoint = new Point(0, 3);
        final Point arrivalPoint = new Point(1, 4);

        // when
        final List<Point> routePoints = general.searchRoutePoints(startPoint, arrivalPoint);

        // then
        assertThat(routePoints).containsExactlyInAnyOrder(
                new Point(1, 4)
        );
    }

    @Test
    @DisplayName("입력한 지점이 궁성 외부일 경우, 예외를 발생시킨다.")
    void test_searchRoutePointsInOutRangeOfPalace() {
        // given
        final Piece general = new General(new Player(1, Team.CHO));
        final Point startPoint = new Point(0, 0);
        final Point arrivalPoint = new Point(1, 1);

        // when
        assertThatThrownBy(() -> general.searchRoutePoints(startPoint, arrivalPoint))
                .isInstanceOf(JanggiGameRuleWarningException.class)
                .hasMessageContaining("해당 기물은 궁성 내에서만 이동 가능 합니다.");
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInMyTeamOnRoute() {
        //given
        final Piece general = new General(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null, general));

        //when&then
        assertThat(general.isMovableOnRoute(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 없으면 이동할 수 있다.")
    void test_isMovableWhenPieceIsInOtherTeamOnRoute() {
        //given
        final Piece generalHan = new General(new Player(1, Team.HAN));
        final Piece generalCho = new General(new Player(1, Team.CHO));
        final PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null, generalCho));

        //when&then
        assertThat(generalHan.isMovableOnRoute(piecesOnRoute)).isTrue();
    }

    @Test
    @DisplayName("궁은 팀에 따라 다르게 이름을 반환한다.")
    void test_toString() {
        //given
        final Piece pieceForCho = new General(new Player(1, Team.CHO));
        final Piece pieceForHan = new General(new Player(1, Team.HAN));

        //when&then
        assertThat(pieceForCho.getName()).isEqualTo("궁");
        assertThat(pieceForHan.getName()).isEqualTo("將");
    }
}
