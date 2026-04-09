package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class SoldierTest {

    @DisplayName("졸의 팀을 확인한다.")
    @ParameterizedTest
    @CsvSource({"HAN", "CHO"})
    void 졸의_팀을_확인한다(Team team) {
        // given
        Soldier soldier = new Soldier(team);

        // when & then
        assertThat(soldier.getTeam()).isEqualTo(team);
    }

    @DisplayName("입력받은 팀과 같은 팀인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 입력받은_팀과_같은_팀인지_확인한다(Team team, boolean expected) {
        // given
        Soldier soldier = new Soldier(team);

        // when
        boolean result = soldier.isSameTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("졸의 타입은 SOLDIER이다.")
    @Test
    void 졸의_타입은_SOLDIER이다() {
        // given
        Soldier soldier = new Soldier(Team.HAN);

        // when
        boolean result = soldier.isSameType(PieceType.SOLDIER);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("전진 또는 옆 방향으로 한 칸 이동시키면 빈 경로를 반환한다.")
    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "43, 53",
            "43, 44",
            "85, 95"})
    void 전진_또는_옆_방향으로_한_칸_이동시키면_빈_경로를_반환한다(String from, String to) {
        // given
        Soldier soldier = new Soldier(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when
        Path path = soldier.getPath(movement);

        // then
        assertThat(path).isEmpty();
    }

    @DisplayName("궁성에서 전진 방향으로 대각선 이동하면 빈 경로를 반환한다.")
    @Test
    void 궁성에서_전진_방향으로_대각선_이동하면_빈_경로를_반환한다() {
        // given
        Soldier soldier = new Soldier(Team.CHO);
        Movement movement = new Movement(Position.from("34"), Position.from("25"));

        // when
        Path path = soldier.getPath(movement);

        // then
        assertThat(path).isEmpty();

    }

    @DisplayName("궁성이 아닌 곳에서 대각선으로 이동시키면 예외가 발생한다.")
    @Test
    void 궁성이_아닌_곳에서_대각선으로_이동시키면_예외가_발생한다() {
        // given
        Soldier soldier = new Soldier(Team.HAN);
        Movement movement = new Movement(Position.from("43"), Position.from("52"));

        // when & then
        assertThatThrownBy(() -> soldier.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸은(는) 해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("뒷 방향으로 이동시키면 예외가 발생한다.")
    @ParameterizedTest(name = "team={0}, from={1}, to={2}")
    @CsvSource({
            "HAN, 45, 35",
            "CHO, 25, 34"})
    void 뒷_방향으로_이동시키면_예외가_발생한다(Team team, String from, String to) {
        // given
        Soldier soldier = new Soldier(team);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when & then
        assertThatThrownBy(() -> soldier.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다.");
    }

    @DisplayName("이동할 위치에 같은 팀이 있으면 예외가 발생한다.")
    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        // given
        Soldier soldier = new Soldier(Team.HAN);

        // when & then
        assertThatThrownBy(() -> soldier.validateCanMove(new PieceOnPath(), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("이동할 위치에 다른 팀이 있으면 예외가 발생하지 않는다.")
    @Test
    void 이동할_위치에_다른_팀이_있으면_예외가_발생하지_않는다() {
        // given
        Soldier soldier = new Soldier(Team.HAN);

        // when & then
        assertThatNoException().isThrownBy(
                () -> soldier.validateCanMove(new PieceOnPath(), new Chariot(Team.CHO)));
    }
}