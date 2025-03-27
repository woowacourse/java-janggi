package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import exceptions.JanggiGameRuleWarningException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class GuardTest {

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void test_hasEqualTeam() {
        //given
        final Piece piece = new Guard(Team.CHO);

        //when&then
        assertThat(piece.hasEqualTeam(Team.CHO)).isTrue();
        assertThat(piece.hasEqualTeam(Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("사의 도착점들를 확인 하고자할 경우, 예외가 발생한다")
    void test_isAbleToArrive() {
        // given
        final Guard guard = new Guard(Team.CHO);

        // when & then
        assertThatThrownBy(() -> guard.isAbleToArrive(new Point(3, 2), new Point(2, 3)))
                .isInstanceOf(JanggiGameRuleWarningException.class);
    }

    @Test
    @DisplayName("사의 이동 가능 여부를 확인할 경우 예외가 발생한다.")
    void test_isMovableOnRoute() {
        // given
        final Piece guard = new Guard(Team.CHO);
        final Piece piece = new Soldier(Team.HAN);
        final PiecesOnRoute pieces = new PiecesOnRoute(List.of(piece));

        // when & then
        assertThatThrownBy(() -> guard.isMovableOnRoute(pieces))
                .isInstanceOf(JanggiGameRuleWarningException.class);
    }

    @Test
    @DisplayName("사의 이동 경로상 좌표를 요청할 경우, 예외가 발생한다.")
    void test_getRoutePoints() {
        // given
        final Piece guard = new Guard(Team.CHO);

        // when & then
        assertThatThrownBy(() -> guard.getRoutePoints(new Point(3, 2), new Point(2, 3)))
                .isInstanceOf(JanggiGameRuleWarningException.class);
    }

    @Test
    @DisplayName("사는 팀에 따라 다르게 이름을 반환한다.")
    void test_toString() {
        //given
        final Piece pieceForCho = new Guard(Team.CHO);
        final Piece pieceForHan = new Guard(Team.HAN);

        //when&then
        assertThat(pieceForCho.getName()).isEqualTo("사");
        assertThat(pieceForHan.getName()).isEqualTo("士");
    }
}
