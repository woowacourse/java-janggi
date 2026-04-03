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

class CannonTest {

    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 포의_팀을_확인한다(Team team, boolean expected) {
        Cannon cannon = new Cannon(team);

        assertThat(cannon.getTeam() == team).isEqualTo(expected);
    }

    @Test
    void 포의_타입은_CANNON이다() {
        Cannon cannon = new Cannon(Team.HAN);

        PieceType type = cannon.getType();

        assertThat(type).isEqualTo(PieceType.CANNON);
    }

    @Test
    void 한_방향으로만_이동시키면_경로를_반환한다() {
        Cannon cannon = new Cannon(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("26"));

        Path path = cannon.getPath(movement);

        assertThat(path).containsExactly(
                Position.from("23"),
                Position.from("24"),
                Position.from("25"));
    }

    @Test
    void 한_방향만으로_이동이_아니면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("33"));

        assertThatThrownBy(() -> cannon.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 직선으로만 이동할 수 있습니다.");
    }

    @Test
    void 이동_가능하면_예외가_발생하지_않는다() {
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new Soldier(Team.HAN));

        assertThatNoException().isThrownBy(
                () -> cannon.validateCanMove(pieceOnPath, new Chariot(Team.CHO)));
    }

    @Test
    void 경로에_존재하는_기물_중_빈_기물이_아닌_기물이_2개_이상이면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Soldier(Team.HAN));
        pieceOnPath.add(new Elephant(Team.HAN));

        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
    }

    @Test
    void 경로에_존재하는_기물이_모두_빈_기물이면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new EmptyPiece());

        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
    }

    @Test
    void 오직_한_칸만_이동하면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.validateCanMove(new PieceOnPath(), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
    }

    @Test
    void 경로에_존재하는_기물이_포면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new Cannon(Team.HAN));

        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
    }

    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Guard(Team.HAN));

        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @Test
    void 이동할_위치에_적의_포가_있으면_예외가_발생한다() {
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Guard(Team.HAN));

        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new Cannon(Team.CHO)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 잡을 수 없습니다.");
    }
}
