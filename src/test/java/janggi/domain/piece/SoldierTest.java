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

@DisplayName("졸/병(Soldier) 기물 테스트")
class SoldierTest {

    @DisplayName("같은 팀의 기물인지 확인하면 올바른 결과를 반환한다.")
    @Test
    void isSameTeam() {
        Soldier hanSoldier = new Soldier(Team.HAN);
        Soldier sameTeamSoldier = new Soldier(Team.HAN);
        Soldier diffTeamSoldier = new Soldier(Team.CHO);

        assertAll(
                () -> assertThat(hanSoldier.isSameTeam(sameTeamSoldier)).isTrue(),
                () -> assertThat(hanSoldier.isSameTeam(diffTeamSoldier)).isFalse()
        );
    }

    @DisplayName("전진 또는 옆으로 1칸 이동하면 정상적으로 경로를 반환한다.")
    @Test
    void getPath_ValidMove() {
        Soldier soldier = new Soldier(Team.HAN);

        List<Position> path = soldier.getPath(Position.from("43"), Position.from("53"));

        assertThat(path).isEmpty();
    }

    @DisplayName("대각선이나 1칸을 초과하여 이동시키려 하면 예외가 발생한다.")
    @Test
    void getPath_InvalidMove_ThrowsException() {
        Soldier soldier = new Soldier(Team.HAN);

        assertAll(
                () -> assertThatThrownBy(() -> soldier.getPath(Position.from("43"), Position.from("54")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 졸은 해당 위치로 이동할 수 없습니다."),
                () -> assertThatThrownBy(() -> soldier.getPath(Position.from("43"), Position.from("63")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 졸은 해당 위치로 이동할 수 없습니다.")
        );
    }

    @DisplayName("자신의 진영에 따라 뒤로 이동하려 하면 예외가 발생한다.")
    @Test
    void getPath_MoveBackward_ThrowsException() {
        Soldier hanSoldier = new Soldier(Team.HAN);
        Soldier choSoldier = new Soldier(Team.CHO);

        assertAll(
                () -> assertThatThrownBy(() -> hanSoldier.getPath(Position.from("45"), Position.from("35")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다."),
                () -> assertThatThrownBy(() -> choSoldier.getPath(Position.from("75"), Position.from("85")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다.")
        );
    }

    @DisplayName("도착 위치에 아군 기물이 있으면 예외가 발생한다.")
    @Test
    void canMove_TargetIsSameTeam_ThrowsException() {
        Soldier soldier = new Soldier(Team.HAN);

        assertThatThrownBy(() -> soldier.canMove(new PiecesOnPath(List.of()), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("도착 위치에 적군 기물이 있으면 정상적으로 이동(공격) 가능하다.")
    @Test
    void canMove_TargetIsDiffTeam_DoesNotThrow() {
        Soldier soldier = new Soldier(Team.HAN);

        assertThatNoException()
                .isThrownBy(() -> soldier.canMove(new PiecesOnPath(List.of()), new Chariot(Team.CHO)));
    }
}
