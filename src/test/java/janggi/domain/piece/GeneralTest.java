package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class GeneralTest {

    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 장의_팀을_확인한다(Team team, boolean expected) {
        General general = new General(team);

        assertThat(general.getTeam() == team).isEqualTo(expected);
    }

    @Test
    void 장의_타입은_GENERAL이다() {
        General general = new General(Team.HAN);

        PieceType type = general.getType();

        assertThat(type).isEqualTo(PieceType.GENERAL);
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "11, 12",
            "11, 21"})
    void 직선_한_칸을_이동시키면_빈_경로를_반환한다(String from, String to) {
        General general = new General(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        Path path = general.getPath(movement);

        assertThat(path).isEmpty();
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "11, 22",
            "11, 15"})
    void 올바르지_않은_경로로_이동시키면_예외가_발생한다(String from, String to) {
        General general = new General(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

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
    void 이동할_위치에_다른_팀이_있으면_예외가_발생하지_않는다() {
        General general = new General(Team.HAN);

        assertThatNoException().isThrownBy(
                () -> general.validateCanMove(new PieceOnPath(), new Chariot(Team.CHO)));
    }
}
