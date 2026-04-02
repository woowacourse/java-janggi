package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ChariotTest {

    @Test
    void 팀_확인_테스트() {
        Chariot chariot = new Chariot(Team.HAN);

        boolean hanResult = chariot.getTeam() == Team.HAN;
        boolean choResult = chariot.getTeam() == Team.CHO;

        assertAll(
                () -> assertThat(hanResult).isTrue(),
                () -> assertThat(choResult).isFalse()
        );
    }

    @Test
    void 차의_타입은_CHARIOT이다() {
        Chariot chariot = new Chariot(Team.HAN);

        PieceType type = chariot.getType();
        assertThat(type).isEqualTo(PieceType.CHARIOT);
    }

    @Test
    void 한_방향으로_된_좌표로_경로를_요청하면_경로를_반환한다() {
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("26"));

        Path path = chariot.getPath(movement);

        assertThat(path).containsExactly(
                Position.from("23"),
                Position.from("24"),
                Position.from("25"));
    }

    @Test
    void 차를_대각선으로_이동시키면_예외가_발생한다() {
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("33"));

        assertThatThrownBy(() -> chariot.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차는 직선으로만 이동할 수 있습니다.");
    }

    @Test
    void 차를_여러_방향으로_이동시키면_예외가_발생한다() {
        Chariot chariot = new Chariot(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("48"));


        assertThatThrownBy(() -> chariot.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차는 직선으로만 이동할 수 있습니다.");
    }

    @Test
    void 경로에_존재하는_기물_중_빈_기물이_아닌_기물이_있으면_예외가_발생한다() {
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
    void 이동_가능_확인_성공_테스트() {
        Chariot chariot = new Chariot(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new EmptyPiece());

        assertThatNoException().isThrownBy(
                () -> chariot.validateCanMove(pieceOnPath, new Chariot(Team.CHO)));
    }
}
