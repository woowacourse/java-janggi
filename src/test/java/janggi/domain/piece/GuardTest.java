package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class GuardTest {

    @DisplayName("사의 팀을 확인한다.")
    @ParameterizedTest
    @CsvSource({"HAN", "CHO"})
    void 사의_팀을_확인한다(Team team) {
        // given
        Guard guard = new Guard(team);

        // when & then
        assertThat(guard.getTeam()).isEqualTo(team);
    }

    @DisplayName("입력받은 팀과 같은 팀인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 입력받은_팀과_같은_팀인지_확인한다(Team team, boolean expected) {
        // given
        Guard guard = new Guard(team);

        // when
        boolean result = guard.isSameTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("사의 타입은 GUARD이다.")
    @Test
    void 사의_타입은_GUARD이다() {
        // given
        Guard guard = new Guard(Team.HAN);

        // when
        boolean result = guard.isSameType(PieceType.GUARD);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("궁성 내에서 직선 한 칸 또는 궁성 길로 이동시키면 빈 경로를 반환한다.")
    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "14, 15",
            "14, 25"})
    void 궁성_내에서_직선_한_칸_또는_궁성_길로_이동시키면_빈_경로를_반환한다(String from, String to) {
        // given
        Guard guard = new Guard(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when
        Path path = guard.getPath(movement);

        // then
        assertThat(path).isEmpty();
    }

    @DisplayName("궁성 내에서 두 칸을 이동하거나 궁성 길이 아닌 길로 이동하면 예외가 발생한다.")
    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "14, 36",
            "15, 24"})
    void 궁성_내에서_두_칸을_이동하거나_궁성_길이_아닌_길로_이동하면_예외가_발생한다(String from, String to) {
        // given
        Guard guard = new Guard(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when & then
        assertThatThrownBy(() -> guard.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사은(는) 해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("궁성 밖으로 이동하면 예외가 발생한다.")
    @Test
    void 궁성_밖으로_이동하면_예외가_발생한다() {
        // given
        Guard guard = new Guard(Team.CHO);
        Movement movement = new Movement(Position.from("85"), Position.from("75"));

        // when & then
        assertThatThrownBy(() -> guard.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사는 궁성 내에서만 움직일 수 있습니다.");
    }

    @DisplayName("이동할 위치에 같은 팀이 있으면 예외가 발생한다.")
    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        // given
        Guard guard = new Guard(Team.HAN);

        // when & then
        assertThatThrownBy(() -> guard.validateCanMove(new PieceOnPath(), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("이동할 위치에 다른 팀이 있으면 예외가 발생하지 않는다.")
    @Test
    void 이동할_위치에_다른_팀이_있으면_예외가_발생하지_않는다() {
        // given
        Guard guard = new Guard(Team.HAN);

        // when & then
        assertThatNoException().isThrownBy(
                () -> guard.validateCanMove(new PieceOnPath(), new Chariot(Team.CHO)));
    }
}