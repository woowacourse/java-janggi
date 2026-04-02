package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.List;

import org.junit.jupiter.api.Test;

public class HorseTest {

    @Test
    void 팀_확인_테스트() {
        Horse horse = new Horse(Team.HAN);

        boolean hanResult = horse.getTeam() == Team.HAN;
        boolean choResult = horse.getTeam() == Team.CHO;

        assertAll(
                () -> assertThat(hanResult).isTrue(),
                () -> assertThat(choResult).isFalse()
        );
    }

    @Test
    void 마의_타입은_HORSE이다() {
        Horse horse = new Horse(Team.HAN);

        PieceType type = horse.getType();
        assertThat(type).isEqualTo(PieceType.HORSE);
    }

    @Test
    void 직선으로_먼저_한_칸_직선_방향의_대각선으로_한_칸_이동시키면_경로를_반환한다() {
        Horse horse = new Horse(Team.HAN);

        Path path = horse.getPath(Position.from("36"), Position.from("57"));

        assertThat(path).containsExactly(Position.from("46"));
    }

    @Test
    void 직선으로_먼저_한_칸_직선_방향의_대각선으로_한_칸_이외의_경로로_이동시키면_예외가_발생한다() {
        Horse horse = new Horse(Team.HAN);

        assertAll(
                () -> assertThatThrownBy(() -> horse.getPath(Position.from("35"), Position.from("65")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 마는 해당 경로로 이동할 수 없습니다."),
                () -> assertThatThrownBy(() -> horse.getPath(Position.from("11"), Position.from("22")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 마는 해당 경로로 이동할 수 없습니다.")
        );
    }

    @Test
    void 경로에_존재하는_기물_중_빈_기물이_아닌_기물이_있으면_예외가_발생한다() {
        Horse horse = new Horse(Team.HAN);

        assertThatThrownBy(() -> horse.canMove(List.of(new Soldier(Team.HAN)), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Horse horse = new Horse(Team.HAN);

        assertThatThrownBy(() -> horse.canMove(List.of(new EmptyPiece()), new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동_가능_확인_성공_테스트() {
        Horse horse = new Horse(Team.HAN);

        boolean result = horse.canMove(List.of(new EmptyPiece()), new Chariot(Team.CHO));

        assertThat(result).isTrue();
    }
}
