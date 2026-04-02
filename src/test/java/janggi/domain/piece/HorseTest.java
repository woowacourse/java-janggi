package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HorseTest {

    @Test
    void 팀_확인_테스트() {
        Horse horse = new Horse(Team.HAN);

        boolean hanResult = horse.getTeam() == Team.HAN;
        boolean choResult = horse.getTeam() == Team.CHO;

        assertAll(
                () -> assertThat(hanResult).isTrue(),
                () -> assertThat(choResult).isFalse()
        );
    }

    @Test
    void 마의_타입은_HORSE이다() {
        Horse horse = new Horse(Team.HAN);

        PieceType type = horse.getType();
        assertThat(type).isEqualTo(PieceType.HORSE);
    }

    @Test
    void 직선으로_먼저_한_칸_직선_방향의_대각선으로_한_칸_이동시키면_경로를_반환한다() {
        Horse horse = new Horse(Team.HAN);
        Movement movement = new Movement(Position.from("36"), Position.from("57"));

        Path path = horse.getPath(movement);

        assertThat(path).containsExactly(Position.from("46"));
    }

    @Test
    void 직선으로_먼저_한_칸_직선_방향의_대각선으로_한_칸_이외의_경로로_이동시키면_예외가_발생한다() {
        Horse horse = new Horse(Team.HAN);
        Movement movement1 = new Movement(Position.from("35"), Position.from("65"));
        Movement movement2 = new Movement(Position.from("11"), Position.from("22"));

        assertAll(
                () -> assertThatThrownBy(() -> horse.getPath(movement1))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 마는 해당 경로로 이동할 수 없습니다."),
                () -> assertThatThrownBy(() -> horse.getPath(movement2))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 마는 해당 경로로 이동할 수 없습니다.")
        );
    }

    @Test
    void 경로에_존재하는_기물_중_빈_기물이_아닌_기물이_있으면_예외가_발생한다() {
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
