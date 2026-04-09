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

class ChariotTest {

    @DisplayName("차의 팀을 확인한다.")
    @ParameterizedTest
    @CsvSource({"HAN", "CHO"})
    void 차의_팀을_확인한다(Team team) {
        // given
        Chariot chariot = new Chariot(team);

        // when & then
        assertThat(chariot.getTeam()).isEqualTo(team);
    }

    @DisplayName("입력받은 팀과 같은 팀인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 입력받은_팀과_같은_팀인지_확인한다(Team team, boolean expected) {
        // given
        Chariot chariot = new Chariot(team);

        // when
        boolean result = chariot.isSameTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("차의 타입은 CHARIOT이다.")
    @Test
    void 차의_타입은_CHARIOT이다() {
        // given
        Chariot chariot = new Chariot(Team.HAN);

        // when
        boolean result = chariot.isSameType(PieceType.CHARIOT);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("한 방향으로 이동시키면 경로를 반환한다.")
    @Test
    void 한_방향으로_이동시키면_경로를_반환한다() {
        // given
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("26"));

        // when
        Path path = chariot.getPath(movement);

        // then
        assertThat(path).containsExactly(
                Position.from("23"),
                Position.from("24"),
                Position.from("25"));
    }

    @DisplayName("궁성에서 대각선으로 이동하면 경로를 반환한다.")
    @ParameterizedTest(name = "from={0}, to={1}, 경로좌표={2}")
    @CsvSource({
            "14, 36, 25",
            "04, 86, 95"})
    void 궁성에서_대각선으로_이동하면_경로를_반환한다(String from, String to, String expectedPosition) {
        // given
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when
        Path path = chariot.getPath(movement);

        // then
        assertThat(path).containsExactly(Position.from(expectedPosition));
    }

    @DisplayName("한 칸을 이동시키면 빈 경로를 반환한다.")
    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "14, 25",
            "22, 23"})
    void 한_칸을_이동시키면_빈_경로를_반환한다(String from, String to) {
        // given
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when
        Path path = chariot.getPath(movement);

        // then
        assertThat(path).isEmpty();
    }

    @DisplayName("궁성 이동이 아닐 때 직선이 아닌 방향으로 이동시키면 예외가 발생한다.")
    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "22, 33",
            "22, 48"})
    void 궁성_이동이_아닐_때_직선이_아닌_방향으로_이동시키면_예외가_발생한다(String from, String to) {
        // given
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when & then
        assertThatThrownBy(() -> chariot.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차은(는) 해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("경로에 기물이 있으면 예외가 발생한다.")
    @Test
    void 경로에_기물이_있으면_예외가_발생한다() {
        // given
        Chariot chariot = new Chariot(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Soldier(Team.HAN));

        // when & then
        assertThatThrownBy(() -> chariot.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @DisplayName("이동할 위치에 같은 팀이 있으면 예외가 발생한다.")
    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        // given
        Chariot chariot = new Chariot(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());

        // when & then
        assertThatThrownBy(() -> chariot.validateCanMove(pieceOnPath, new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("이동 가능하면 예외가 발생하지 않는다.")
    @Test
    void 이동_가능하면_예외가_발생하지_않는다() {
        // given
        Chariot chariot = new Chariot(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new EmptyPiece());

        // when & then
        assertThatNoException().isThrownBy(
                () -> chariot.validateCanMove(pieceOnPath, new Chariot(Team.CHO)));
    }
}
