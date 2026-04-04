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

class SoldierTest {

    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 졸의_팀을_확인한다(Team team, boolean expected) {
        Soldier soldier = new Soldier(team);

        assertThat(soldier.getTeam() == team).isEqualTo(expected);
    }

    @Test
    void 졸의_타입은_SOLDIER이다() {
        Soldier soldier = new Soldier(Team.HAN);

        PieceType type = soldier.getType();

        assertThat(type).isEqualTo(PieceType.SOLDIER);
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "43, 53",
            "43, 44"})
    void 전진_방향으로_한_칸_이동시키면_빈_경로를_반환한다(String from, String to) {
        Soldier soldier = new Soldier(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        Path path = soldier.getPath(movement);

        assertThat(path).isEmpty();
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "43, 54",
            "43, 63"})
    void 올바르지_않은_경로로_이동시키면_예외가_발생한다(String from, String to) {
        Soldier soldier = new Soldier(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        assertThatThrownBy(() -> soldier.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸은 해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest(name = "team={0}, from={1}, to={2}")
    @CsvSource({
            "HAN, 45, 35",
            "CHO, 75, 85"})
    void 뒷_방향으로_이동시키면_예외가_발생한다(Team team, String from, String to) {
        Soldier soldier = new Soldier(team);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        assertThatThrownBy(() -> soldier.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸은 뒷 방향으로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Soldier soldier = new Soldier(Team.HAN);

        assertThatThrownBy(() -> soldier.validateCanMove(new PieceOnPath(), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_다른_팀이_있으면_예외가_발생하지_않는다() {
        Soldier soldier = new Soldier(Team.HAN);

        assertThatNoException().isThrownBy(
                () -> soldier.validateCanMove(new PieceOnPath(), new Chariot(Team.CHO)));
    }
}
