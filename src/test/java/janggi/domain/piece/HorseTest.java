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

class HorseTest {

    @DisplayName("마의 팀을 확인한다.")
    @ParameterizedTest
    @CsvSource({"HAN", "CHO"})
    void 마의_팀을_확인한다(Team team) {
        // given
        Horse horse = new Horse(team);

        // when & then
        assertThat(horse.getTeam()).isEqualTo(team);
    }

    @DisplayName("입력받은 팀과 같은 팀인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 입력받은_팀과_같은_팀인지_확인한다(Team team, boolean expected) {
        // given
        Horse horse = new Horse(team);

        // when
        boolean result = horse.isSameTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("마의 타입은 HORSE이다.")
    @Test
    void 마의_타입은_HORSE이다() {
        // given
        Horse horse = new Horse(Team.HAN);

        // when
        boolean result = horse.isSameType(PieceType.HORSE);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("올바른 경로로 이동시키면 경로를 반환한다.")
    @ParameterizedTest(name = "from={0}, to={1}, path1={2}")
    @CsvSource({
            "36, 57, 46",
            "23, 42, 33"})
    void 올바른_경로로_이동시키면_경로를_반환한다(String from, String to, String path1) {
        // given
        Horse horse = new Horse(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when
        Path path = horse.getPath(movement);

        // then
        assertThat(path).containsExactly(Position.from(path1));
    }

    @DisplayName("올바르지 않은 경로로 이동시키면 예외가 발생한다.")
    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "35, 65",
            "11, 22"})
    void 올바르지_않은_경로로_이동시키면_예외가_발생한다(String from, String to) {
        // given
        Horse horse = new Horse(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when & then
        assertThatThrownBy(() -> horse.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마는 해당 경로로 이동할 수 없습니다.");
    }

    @DisplayName("경로에 기물이 있으면 예외가 발생한다.")
    @Test
    void 경로에_기물이_있으면_예외가_발생한다() {
        // given
        Horse horse = new Horse(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Soldier(Team.HAN));

        // when & then
        assertThatThrownBy(() -> horse.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @DisplayName("이동할 위치에 같은 팀이 있으면 예외가 발생한다.")
    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        // given
        Horse horse = new Horse(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());

        // when & then
        assertThatThrownBy(() -> horse.validateCanMove(pieceOnPath, new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("이동 가능하면 예외가 발생하지 않는다.")
    @Test
    void 이동_가능하면_예외가_발생하지_않는다() {
        // given
        Horse horse = new Horse(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());

        // when & then
        assertThatNoException().isThrownBy(
                () -> horse.validateCanMove(pieceOnPath, new Chariot(Team.CHO)));
    }
}