package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class GeneralTest {

    @ParameterizedTest
    @CsvSource({"HAN", "CHO"})
    void 장의_팀을_확인한다(Team team) {
        // given
        General general = new General(team);

        // when & then
        assertThat(general.getTeam()).isEqualTo(team);
    }

    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 입력받은_팀과_같은_팀인지_확인한다(Team team, boolean expected) {
        // given
        General general = new General(team);

        // when
        boolean result = general.isSameTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void 장의_타입은_GENERAL이다() {
        // given
        General general = new General(Team.HAN);

        // when
        boolean result = general.isSameType(PieceType.GENERAL);

        // then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, false"})
    void 자신의_팀과_같은_팀인지_판별한다(Team team, boolean expected) {
        // given
        General general = new General(Team.HAN);

        // when & then
        assertThat(general.isSameTeam(team)).isEqualTo(expected);
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "25, 35",
            "25, 14"})
    void 궁성_내에서_직선_한_칸_또는_궁성_길로_이동시키면_빈_경로를_반환한다(String from, String to) {
        // given
        General general = new General(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when
        Path path = general.getPath(movement);

        // then
        assertThat(path).isEmpty();
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "14, 36",
            "15, 24"})
    void 궁성_내에서_두_칸을_이동하거나_궁성_길이_아닌_길로_이동하면_예외가_발생한다(String from, String to) {
        // given
        General general = new General(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when & then
        assertThatThrownBy(() -> general.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장은(는) 해당 위치로 이동할 수 없습니다.");
    }

    @Test
    void 궁성_밖으로_이동하면_예외가_발생한다() {
        // given
        General general = new General(Team.CHO);
        Movement movement = new Movement(Position.from("85"), Position.from("75"));

        // when & then
        assertThatThrownBy(() -> general.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장은 궁성 내에서만 움직일 수 있습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        // given
        General general = new General(Team.HAN);

        // when & then
        assertThatThrownBy(() -> general.validateCanMove(new PieceOnPath(), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_다른_팀이_있으면_예외가_발생하지_않는다() {
        // given
        General general = new General(Team.HAN);

        // when & then
        assertThatNoException().isThrownBy(
                () -> general.validateCanMove(new PieceOnPath(), new Chariot(Team.CHO)));
    }
}
