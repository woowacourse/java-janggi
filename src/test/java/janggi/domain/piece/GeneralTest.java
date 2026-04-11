package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("장(General) 기물 테스트")
class GeneralTest {

    @DisplayName("같은 팀의 기물인지 확인하면 올바른 결과를 반환한다.")
    @Test
    void isSameTeam() {
        General hanGeneral = new General(Team.HAN);
        General sameTeamGeneral = new General(Team.HAN);
        General diffTeamGeneral = new General(Team.CHO);

        assertAll(
                () -> assertThat(hanGeneral.isSameTeam(sameTeamGeneral)).isTrue(),
                () -> assertThat(hanGeneral.isSameTeam(diffTeamGeneral)).isFalse()
        );
    }

    @DisplayName("궁성 내에서 직선으로 1칸 이동하면 정상적으로 경로를 반환한다.")
    @Test
    void getPath_ValidStraightMove() {
        General general = new General(Team.HAN);

        List<Position> path = general.getPath(Position.from("25"), Position.from("24"));

        assertThat(path).isEmpty();
    }

    @DisplayName("궁성 내 대각선 선을 따라 1칸 이동하면 정상적으로 경로를 반환한다.")
    @Test
    void getPath_ValidDiagonalMove() {
        General general = new General(Team.HAN);

        List<Position> path = general.getPath(Position.from("25"), Position.from("14"));

        assertThat(path).isEmpty();
    }

    @DisplayName("궁성 밖으로 이동하려 하면 예외가 발생한다.")
    @Test
    void getPath_MoveOutsidePalace_ThrowsException() {
        General general = new General(Team.HAN);

        assertThatThrownBy(() -> general.getPath(Position.from("14"), Position.from("13")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장은 해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("대각선 선 위가 아닌 곳으로 대각선 이동하거나 1칸을 초과하면 예외가 발생한다.")
    @Test
    void getPath_InvalidMove_ThrowsException() {
        General general = new General(Team.HAN);

        assertAll(
                () -> assertThatThrownBy(() -> general.getPath(Position.from("15"), Position.from("24")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 장은 해당 위치로 이동할 수 없습니다."),
                () -> assertThatThrownBy(() -> general.getPath(Position.from("14"), Position.from("36")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 장은 해당 위치로 이동할 수 없습니다.")
        );
    }

    @DisplayName("도착 위치에 아군 기물이 있으면 이동할 수 없고 예외가 발생한다.")
    @Test
    void canMove_TargetIsSameTeam_ThrowsException() {
        General general = new General(Team.HAN);

        assertThatThrownBy(() -> general.canMove(new PiecesOnPath(List.of()), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("도착 위치에 적군 기물이 있으면 정상적으로 이동(공격) 가능하다.")
    @Test
    void canMove_TargetIsDiffTeam_DoesNotThrow() {
        General general = new General(Team.HAN);

        assertThatNoException()
                .isThrownBy(() -> general.canMove(new PiecesOnPath(List.of()), new Chariot(Team.CHO)));
    }
}
