package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ChariotTest {

    @Test
    void 팀_확인_테스트() {
        Chariot chariot = new Chariot(Team.HAN);

        boolean hanResult = chariot.getTeam() == Team.HAN;
        boolean choResult = chariot.getTeam() == Team.CHO;

        assertAll(
                () -> assertThat(hanResult).isTrue(),
                () -> assertThat(choResult).isFalse()
        );
    }

    @Test
    void 차의_이름은_차로_표현된다() {
        Chariot chariot = new Chariot(Team.HAN);

        String displayName = chariot.getDisplayName();
        assertThat(displayName).isEqualTo("차");
    }

    @Test
    void 한_방향으로_된_좌표로_경로를_요청하면_경로를_반환한다() {
        Chariot chariot = new Chariot(Team.HAN);

        List<Position> path = chariot.getPath(Position.from("22"), Position.from("26"));

        assertThat(path).containsExactly(
                Position.from("23"),
                Position.from("24"),
                Position.from("25"));
    }

    @Test
    void 차를_대각선으로_이동시키면_예외가_발생한다() {
        Chariot chariot = new Chariot(Team.HAN);

        assertThatThrownBy(() -> chariot.getPath(Position.from("22"), Position.from("33")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차는 직선으로만 이동할 수 있습니다.");
    }

    @Test
    void 차를_여러_방향으로_이동시키면_예외가_발생한다() {
        Chariot chariot = new Chariot(Team.HAN);

        assertThatThrownBy(() -> chariot.getPath(Position.from("22"), Position.from("48")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차는 직선으로만 이동할 수 있습니다.");
    }

    @Test
    void 경로에_존재하는_기물_중_빈_기물이_아닌_기물이_있으면_예외가_발생한다() {
        Chariot chariot = new Chariot(Team.HAN);

        assertThatThrownBy(() -> chariot.canMove(List.of(new Soldier(Team.HAN)), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Chariot chariot = new Chariot(Team.HAN);

        assertThatThrownBy(() -> chariot.canMove(List.of(new EmptyPiece()), new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동_가능_확인_성공_테스트() {
        Chariot chariot = new Chariot(Team.HAN);

        boolean result = chariot.canMove(List.of(new EmptyPiece(), new EmptyPiece()), new Chariot(Team.CHO));

        assertThat(result).isTrue();
    }
}
