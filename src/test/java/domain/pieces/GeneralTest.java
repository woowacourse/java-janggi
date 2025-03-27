package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.player.TeamType;
import exceptions.JanggiGameRuleWarningException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class GeneralTest {

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void test_hasEqualTeam() {
        //given
        final Piece piece = new General(TeamType.CHO);

        //when&then
        assertThat(piece.hasEqualTeam(TeamType.CHO)).isTrue();
        assertThat(piece.hasEqualTeam(TeamType.HAN)).isFalse();
    }

    @Test
    @DisplayName("궁의 도착점들을 확인하고자 할 경우, 예외를 던진다")
    void test_isAbleToArrive() {
        // given
        final General general = new General(TeamType.CHO);

        // when & then
        assertThatThrownBy(() -> general.isAbleToArrive(new Point(3, 2), new Point(2, 3)))
                .isInstanceOf(JanggiGameRuleWarningException.class);
    }

    @Test
    @DisplayName("궁을 이동 가능 여부를 확인할 경우, 예외가 발생한다.")
    void test_isMovableOnRoute() {
        // given
        final General general = new General(TeamType.CHO);
        final Piece piece = new Soldier(TeamType.HAN);
        final PiecesOnRoute pieces = new PiecesOnRoute(List.of(piece));

        // when & then
        assertThatThrownBy(() -> general.isMovableOnRoute(pieces))
                .isInstanceOf(JanggiGameRuleWarningException.class);
    }

    @Test
    @DisplayName("궁의 이동 경로상 좌표를 요청할 경우, 예외가 발생한다.")
    void test_getRoutePoints() {
        // given
        final General general = new General(TeamType.CHO);

        // when & then
        assertThatThrownBy(() -> general.getRoutePoints(new Point(3, 2), new Point(2, 3)))
                .isInstanceOf(JanggiGameRuleWarningException.class);
    }


    @Test
    @DisplayName("궁은 팀에 따라 다르게 이름을 반환한다.")
    void test_toString() {
        //given
        final Piece pieceForCho = new General(TeamType.CHO);
        final Piece pieceForHan = new General(TeamType.HAN);

        //when&then
        assertThat(pieceForCho.getName()).isEqualTo("궁");
        assertThat(pieceForHan.getName()).isEqualTo("將");
    }
}
