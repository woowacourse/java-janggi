package janggi.domain.piece;

import janggi.domain.team.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class GuardTest {

    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 사의_팀을_확인한다(Team team, boolean expected) {
        Guard guard = new Guard(team);

        assertThat(guard.getTeam() == team).isEqualTo(expected);
    }

    @Test
    void 사의_타입은_GUARD이다() {
        Guard guard = new Guard(Team.HAN);

        PieceType type = guard.getType();

        assertThat(type).isEqualTo(PieceType.GUARD);
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "11, 12",
            "11, 21"})
    void 직선_한_칸을_이동시키면_빈_경로를_반환한다(String from, String to) {
        Guard guard = new Guard(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        Path path = guard.getPath(movement);

        assertThat(path).isEmpty();
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "11, 22",
            "43, 54"})
    void 올바르지_않은_경로로_이동시키면_예외가_발생한다(String from, String to) {
        Guard guard = new Guard(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        assertThatThrownBy(() -> guard.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사는 해당 위치로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Guard guard = new Guard(Team.HAN);

        assertThatThrownBy(() -> guard.validateCanMove(new PieceOnPath(), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_다른_팀이_있으면_예외가_발생하지_않는다() {
        Guard guard = new Guard(Team.HAN);

        assertThatNoException().isThrownBy(
                () -> guard.validateCanMove(new PieceOnPath(), new Chariot(Team.CHO)));
    }
}