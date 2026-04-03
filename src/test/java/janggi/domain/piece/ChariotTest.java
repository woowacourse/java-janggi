package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChariotTest {

    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 차의_팀을_확인한다(Team team, boolean expected) {
        Chariot chariot = new Chariot(team);

        assertThat(chariot.getTeam() == team).isEqualTo(expected);
    }

    @Test
    void 차의_타입은_CHARIOT이다() {
        Chariot chariot = new Chariot(Team.HAN);

        PieceType type = chariot.getType();

        assertThat(type).isEqualTo(PieceType.CHARIOT);
    }

    @Test
    void 한_방향으로_이동시키면_경로를_반환한다() {
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("26"));

        Path path = chariot.getPath(movement);

        assertThat(path).containsExactly(
                Position.from("23"),
                Position.from("24"),
                Position.from("25"));
    }

    @Test
    void 한_칸을_이동시키면_빈_경로를_반환한다() {
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("23"));

        Path path = chariot.getPath(movement);

        assertThat(path).isEmpty();
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "22, 33",
            "22, 48"})
    void 직선이_아닌_방향으로_이동시키면_예외가_발생한다(String from, String to) {
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        assertThatThrownBy(() -> chariot.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차는 직선으로만 이동할 수 있습니다.");
    }

    @Test
    void 경로에_기물이_있으면_예외가_발생한다() {
        Chariot chariot = new Chariot(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Soldier(Team.HAN));

        assertThatThrownBy(() -> chariot.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Chariot chariot = new Chariot(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());

        assertThatThrownBy(() -> chariot.validateCanMove(pieceOnPath, new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동_가능하면_예외가_발생하지_않는다() {
        Chariot chariot = new Chariot(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new EmptyPiece());

        assertThatNoException().isThrownBy(
                () -> chariot.validateCanMove(pieceOnPath, new Chariot(Team.CHO)));
    }
}
