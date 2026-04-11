package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("차(Chariot) 기물 테스트")
class ChariotTest {

    @DisplayName("같은 팀의 기물인지 확인하면 올바른 결과를 반환한다.")
    @Test
    void isSameTeam() {
        Chariot hanChariot = new Chariot(Team.HAN);
        Chariot sameTeamChariot = new Chariot(Team.HAN);
        Chariot diffTeamChariot = new Chariot(Team.CHO);

        assertAll(
                () -> assertThat(hanChariot.isSameTeam(sameTeamChariot)).isTrue(),
                () -> assertThat(hanChariot.isSameTeam(diffTeamChariot)).isFalse()
        );
    }

    @DisplayName("직선 방향으로 이동시키면 통과하는 모든 경로 좌표를 반환한다.")
    @Test
    void getPath_ValidStraightMove() {
        Chariot chariot = new Chariot(Team.HAN);
        Position from = Position.from("22");
        Position to = Position.from("26");

        List<Position> path = chariot.getPath(from, to);

        assertThat(path).containsExactly(
                Position.from("23"),
                Position.from("24"),
                Position.from("25")
        );
    }

    @DisplayName("궁성 대각선 선을 따라 1칸 이동하면 빈 경로를 반환한다.")
    @Test
    void getPath_DiagonalOneStepInPalace() {
        Chariot chariot = new Chariot(Team.HAN);

        List<Position> path = chariot.getPath(Position.from("25"), Position.from("14"));

        assertThat(path).isEmpty();
    }

    @DisplayName("궁성 대각선 선을 따라 2칸 이동하면 중심 위치를 경로로 반환한다.")
    @Test
    void getPath_DiagonalTwoStepInPalace() {
        Chariot chariot = new Chariot(Team.HAN);

        List<Position> path = chariot.getPath(Position.from("14"), Position.from("36"));

        assertThat(path).containsExactly(Position.from("25"));
    }

    @DisplayName("직선이 아닌 곳으로 이동시키려 하면 예외가 발생한다.")
    @Test
    void getPath_NotStraightMove_ThrowsException() {
        Chariot chariot = new Chariot(Team.HAN);

        assertAll(
                () -> assertThatThrownBy(() -> chariot.getPath(Position.from("22"), Position.from("33")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 차는 직선으로만 이동할 수 있습니다."),
                () -> assertThatThrownBy(() -> chariot.getPath(Position.from("22"), Position.from("48")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 차는 직선으로만 이동할 수 있습니다.")
        );
    }

    @DisplayName("차의 이동 경로에 기물이 있으면 예외가 발생한다.")
    @Test
    void canMove_PathBlocked_ThrowsException() {
        Chariot chariot = new Chariot(Team.HAN);

        assertThatThrownBy(() -> chariot.canMove(new PiecesOnPath(List.of(new Soldier(Team.HAN))), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @DisplayName("궁성 2칸 대각선 이동 시 중심 위치에 기물이 있으면 예외가 발생한다.")
    @Test
    void canMove_PalaceDiagonalTwoStep_CenterBlocked_ThrowsException() {
        Chariot chariot = new Chariot(Team.HAN);

        assertThatThrownBy(() -> chariot.canMove(new PiecesOnPath(List.of(new Soldier(Team.CHO))), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @DisplayName("도착 위치에 아군 기물이 있으면 이동할 수 없고 예외가 발생한다.")
    @Test
    void canMove_TargetIsSameTeam_ThrowsException() {
        Chariot chariot = new Chariot(Team.HAN);

        assertThatThrownBy(() -> chariot.canMove(new PiecesOnPath(List.of(new EmptyPiece())), new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("경로가 뚫려 있고 도착 위치에 적군 기물이 있으면 정상적으로 이동 가능하다.")
    @Test
    void canMove_ValidPathAndTarget_DoesNotThrow() {
        Chariot chariot = new Chariot(Team.HAN);

        assertThatNoException()
                .isThrownBy(() -> chariot.canMove(new PiecesOnPath(List.of(new EmptyPiece(), new EmptyPiece())), new Chariot(Team.CHO)));
    }
}
