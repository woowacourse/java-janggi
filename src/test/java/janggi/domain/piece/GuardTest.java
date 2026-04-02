package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GuardTest {

    @Test
    void 팀_확인_테스트() {
        Guard guard = new Guard(Team.HAN);

        boolean hanResult = guard.getTeam() == Team.HAN;
        boolean choResult = guard.getTeam() == Team.CHO;

        assertAll(
                () -> assertThat(hanResult).isTrue(),
                () -> assertThat(choResult).isFalse()
        );
    }

    @Test
    void 사의_타입은_GUARD이다() {
        Guard guard = new Guard(Team.HAN);

        PieceType type = guard.getType();
        assertThat(type).isEqualTo(PieceType.GUARD);
    }

    @Test
    void 직선_한_칸을_이동시키면_경로를_반환한다() {
        Guard guard = new Guard(Team.HAN);

        Path path = guard.getPath(Position.from("11"), Position.from("12"));

        assertThat(path).hasSize(0);
    }

    @Test
    void 대각선_한_칸을_이동시키면_경로를_반환한다() {
        Guard guard = new Guard(Team.HAN);

        Path path = guard.getPath(Position.from("11"), Position.from("22"));

        assertThat(path).hasSize(0);
    }

    @Test
    void 직선_한_칸보다_많이_이동시키면_예외를_발생한다() {
        Guard guard = new Guard(Team.HAN);

        assertThatThrownBy(() -> guard.getPath(Position.from("11"), Position.from("15")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사는 해당 위치로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Guard guard = new Guard(Team.HAN);

        assertThatThrownBy(() -> guard.canMove(List.of(), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_다른_팀이_있으면_참을_반환한다() {
        Guard guard = new Guard(Team.HAN);

        boolean result = guard.canMove(List.of(), new Chariot(Team.CHO));

        assertThat(result).isTrue();
    }
}
