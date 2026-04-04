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

class HorseTest {

    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 마의_팀을_확인한다(Team team, boolean expected) {
        Horse horse = new Horse(team);

        assertThat(horse.getTeam() == team).isEqualTo(expected);
    }

    @Test
    void 마의_타입은_HORSE이다() {
        Horse horse = new Horse(Team.HAN);

        PieceType type = horse.getType();

        assertThat(type).isEqualTo(PieceType.HORSE);
    }

    @ParameterizedTest(name = "from={0}, to={1}, path1={2}")
    @CsvSource({
            "36, 57, 46",
            "23, 42, 33"})
    void 올바른_경로로_이동시키면_경로를_반환한다(String from, String to, String path1) {
        Horse horse = new Horse(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        Path path = horse.getPath(movement);

        assertThat(path).containsExactly(Position.from(path1));
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "35, 65",
            "11, 22"})
    void 올바르지_않은_경로로_이동시키면_예외가_발생한다(String from, String to) {
        Horse horse = new Horse(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        assertThatThrownBy(() -> horse.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마는 해당 경로로 이동할 수 없습니다.");
    }

    @Test
    void 경로에_기물이_있으면_예외가_발생한다() {
        Horse horse = new Horse(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Soldier(Team.HAN));

        assertThatThrownBy(() -> horse.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Horse horse = new Horse(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());

        assertThatThrownBy(() -> horse.validateCanMove(pieceOnPath, new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동_가능하면_예외가_발생하지_않는다() {
        Horse horse = new Horse(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());

        assertThatNoException().isThrownBy(
                () -> horse.validateCanMove(pieceOnPath, new Chariot(Team.CHO)));
    }
}
