package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CannonTest {

    @Test
    void 같은_팀의_포이면_참을_반환한다() {
        Cannon hanCannon1 = new Cannon(Team.HAN);
        Cannon hanCannon2 = new Cannon(Team.HAN);

        boolean result = hanCannon1.isSameTeam(hanCannon2);

        assertThat(result).isTrue();
    }

    @Test
    void 서로_다른_팀의_포이면_참을_반환한다() {
        Cannon hanCannon = new Cannon(Team.HAN);
        Cannon choCannon = new Cannon(Team.CHO);

        boolean result = hanCannon.isSameTeam(choCannon);

        assertThat(result).isFalse();
    }

    @Test
    void 포의_이름은_포로_표현된다() {
        Cannon cannon = new Cannon(Team.HAN);

        String displayName = cannon.getDisplayName();
        assertThat(displayName).isEqualTo("포");
    }

    @Test
    void 한_방향만으로_이동이_아니면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.getPath(Position.from("22"), Position.from("33")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 직선으로만 이동할 수 있습니다.");
    }

    @Test
    void 경로에_존재하는_기물_중_빈_기물이_아닌_기물이_2개_이상이면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(List.of(new Soldier(Team.HAN), new Elephant(Team.HAN)), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
    }

    @Test
    void 경로에_존재하는_기물이_모두_빈_기물이면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(List.of(new EmptyPiece(), new EmptyPiece()), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
    }

    @Test
    void 오직_한_칸만_이동하면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(List.of(), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
    }

    @Test
    void 경로에_존재하는_기물이_포면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(List.of(new EmptyPiece(), new Cannon(Team.HAN)), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(List.of(new Guard(Team.HAN)), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_적의_포가_있으면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(List.of(new Guard(Team.HAN)), new Cannon(Team.CHO)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 잡을 수 없습니다.");
    }

    @Test
    void 한_방향으로만_이동시키면_경로를_반환한다() {
        Cannon cannon = new Cannon(Team.HAN);

        List<Position> path = cannon.getPath(Position.from("22"), Position.from("26"));

        assertThat(path).containsExactly(
                Position.from("23"),
                Position.from("24"),
                Position.from("25"));
    }

    @Test
    void 이동_가능_확인_성공_테스트() {
        Cannon cannon = new Cannon(Team.HAN);

        boolean result = cannon.canMove(List.of(new EmptyPiece(), new Soldier(Team.HAN)), new Chariot(Team.CHO));

        assertThat(result).isTrue();
    }
}
