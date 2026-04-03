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

class ElephantTest {

    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 상의_팀을_확인한다(Team team, boolean expected) {
        Elephant elephant = new Elephant(team);

        assertThat(elephant.getTeam() == team).isEqualTo(expected);
    }

    @Test
    void 상의_타입은_ELEPHANT이다() {
        Elephant elephant = new Elephant(Team.HAN);

        PieceType type = elephant.getType();

        assertThat(type).isEqualTo(PieceType.ELEPHANT);
    }

    @ParameterizedTest(name = "from={0}, to={1}, path1={2}, path2={3}")
    @CsvSource({
            "13, 45, 23, 34",
            "13, 41, 23, 32"})
    void 올바른_경로로_이동시키면_경로를_반환한다(String from, String to, String path1, String path2) {
        Elephant elephant = new Elephant(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        Path path = elephant.getPath(movement);

        assertThat(path).containsExactly(Position.from(path1), Position.from(path2));
    }

    @ParameterizedTest(name = "from={0}, to={1}")
    @CsvSource({
            "35, 65",
            "11, 33"})
    void 올바르지_않은_경로로_이동시키면_예외가_발생한다(String from, String to) {
        Elephant elephant = new Elephant(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        assertThatThrownBy(() -> elephant.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상은 해당 경로로 이동할 수 없습니다.");
    }

    @Test
    void 경로에_기물이_있으면_예외가_발생한다() {
        Elephant elephant = new Elephant(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new Soldier(Team.HAN));

        assertThatThrownBy(() -> elephant.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Elephant elephant = new Elephant(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new EmptyPiece());

        assertThatThrownBy(() -> elephant.validateCanMove(pieceOnPath, new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동_가능하면_예외가_발생하지_않는다() {
        Elephant elephant = new Elephant(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new EmptyPiece());

        assertThatNoException().isThrownBy(
                () -> elephant.validateCanMove(pieceOnPath, new Chariot(Team.CHO)));
    }
}