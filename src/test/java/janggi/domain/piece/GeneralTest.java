package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GeneralTest {

    @Test
    void 팀_확인_테스트() {
        General general = new General(Team.HAN);

        boolean hanResult = general.getTeam() == Team.HAN;
        boolean choResult = general.getTeam() == Team.CHO;

        assertAll(
                () -> assertThat(hanResult).isTrue(),
                () -> assertThat(choResult).isFalse()
        );
    }

    @Test
    void 장의_타입은_GENERAL이다() {
        General general = new General(Team.HAN);

        PieceType type = general.getType();
        assertThat(type).isEqualTo(PieceType.GENERAL);
    }

    @Test
    void 직선_한_칸을_이동시키면_경로를_반환한다() {
        General general = new General(Team.HAN);
        Movement movement = new Movement(Position.from("11"), Position.from("12"));

        Path path = general.getPath(movement);

        assertThat(path).hasSize(0);
    }

    @Test
    void 대각선_한_칸을_이동시키면_경로를_반환한다() {
        General general = new General(Team.HAN);
        Movement movement = new Movement(Position.from("11"), Position.from("22"));

        Path path = general.getPath(movement);

        assertThat(path).hasSize(0);
    }

    @Test
    void 직선_한_칸보다_많이_이동시키면_예외를_발생한다() {
        General general = new General(Team.HAN);
        Movement movement = new Movement(Position.from("11"), Position.from("15"));

        assertThatThrownBy(() -> general.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장은 해당 위치로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        General general = new General(Team.HAN);

        assertThatThrownBy(() -> general.validateCanMove(new PieceOnPath(), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_다른_팀이_있으면_예외를_반환하지_않는다() {
        General general = new General(Team.HAN);

        assertThatNoException().isThrownBy(
                () -> general.validateCanMove(new PieceOnPath(), new Chariot(Team.CHO)));
    }
}
