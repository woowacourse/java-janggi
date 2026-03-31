package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SoldierTest {

    @Test
    void 팀_확인_테스트() {
        Soldier soldier = new Soldier(Team.HAN);

        boolean hanResult = soldier.getTeam() == Team.HAN;
        boolean choResult = soldier.getTeam() == Team.CHO;

        assertAll(
                () -> assertThat(hanResult).isTrue(),
                () -> assertThat(choResult).isFalse()
        );
    }

    @Test
    void 졸의_이름은_졸로_표현된다() {
        Soldier soldier = new Soldier(Team.HAN);

        String displayName = soldier.getDisplayName();
        assertThat(displayName).isEqualTo("졸");
    }

    @Test
    void 뒷_방향이_아닌_직선_한_칸을_이동시키면_경로를_반환한다() {
        Soldier soldier = new Soldier(Team.HAN);

        List<Position> path = soldier.getPath(Position.from("43"), Position.from("53"));

        assertThat(path).hasSize(0);
    }

    @Test
    void 대각선_이동시키면_예외가_발생한다() {
        Soldier soldier = new Soldier(Team.HAN);

        assertThatThrownBy(() -> soldier.getPath(Position.from("43"), Position.from("54")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸은 해당 위치로 이동할 수 없습니다.");
    }

    @Test
    void 직선_한_칸보다_많이_이동시키면_예외를_발생한다() {
        Soldier soldier = new Soldier(Team.HAN);

        assertThatThrownBy(() -> soldier.getPath(Position.from("43"), Position.from("63")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸은 해당 위치로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Soldier soldier = new Soldier(Team.HAN);

        assertThatThrownBy(() -> soldier.canMove(List.of(), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_다른_팀이_있으면_참을_반환한다() {
        Soldier soldier = new Soldier(Team.HAN);

        boolean result = soldier.canMove(List.of(), new Chariot(Team.CHO));

        assertThat(result).isTrue();
    }

    @Test
    void 한나라일때_위_방향으로_한_칸_이동시키면_예외가_발생한다() {
        Soldier soldier = new Soldier(Team.HAN);

        assertThatThrownBy(() -> soldier.getPath(Position.from("45"), Position.from("35")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다.");
    }

    @Test
    void 초나라일때_아래_방향으로_한_칸_이동시키면_예외가_발생한다() {
        Soldier soldier = new Soldier(Team.CHO);

        assertThatThrownBy(() -> soldier.getPath(Position.from("75"), Position.from("85")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다.");
    }
}
