package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.List;

import org.junit.jupiter.api.Test;

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

        Path path = general.getPath(Position.from("11"), Position.from("12"));

        assertThat(path).hasSize(0);
    }

    @Test
    void 대각선_한_칸을_이동시키면_경로를_반환한다() {
        General general = new General(Team.HAN);

        Path path = general.getPath(Position.from("11"), Position.from("22"));

        assertThat(path).hasSize(0);
    }

    @Test
    void 직선_한_칸보다_많이_이동시키면_예외를_발생한다() {
        General general = new General(Team.HAN);

        assertThatThrownBy(() -> general.getPath(Position.from("11"), Position.from("15")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장은 해당 위치로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        General general = new General(Team.HAN);

        assertThatThrownBy(() -> general.canMove(List.of(), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_다른_팀이_있으면_참을_반환한다() {
        General general = new General(Team.HAN);

        boolean result = general.canMove(List.of(), new Chariot(Team.CHO));

        assertThat(result).isTrue();
    }
}
