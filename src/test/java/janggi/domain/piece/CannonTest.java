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

@DisplayName("포(Cannon) 기물 테스트")
class CannonTest {

    @DisplayName("같은 팀의 기물인지 확인하면 올바른 결과를 반환한다.")
    @Test
    void isSameTeam() {
        Cannon hanCannon = new Cannon(Team.HAN);
        Cannon sameTeamCannon = new Cannon(Team.HAN);
        Cannon diffTeamCannon = new Cannon(Team.CHO);

        assertAll(
                () -> assertThat(hanCannon.isSameTeam(sameTeamCannon)).isTrue(),
                () -> assertThat(hanCannon.isSameTeam(diffTeamCannon)).isFalse()
        );
    }

    @DisplayName("직선 방향으로 이동시키면 통과하는 모든 경로 좌표를 반환한다.")
    @Test
    void getPath_ValidStraightMove() {
        Cannon cannon = new Cannon(Team.HAN);
        Position from = Position.from("22");
        Position to = Position.from("26");

        List<Position> path = cannon.getPath(from, to);

        assertThat(path).containsExactly(
                Position.from("23"),
                Position.from("24"),
                Position.from("25")
        );
    }

    @DisplayName("직선 방향이 아닌 대각선 등으로 이동시키려 하면 예외가 발생한다.")
    @Test
    void getPath_NotStraightMove_ThrowsException() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.getPath(Position.from("22"), Position.from("33")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 직선으로만 이동할 수 있습니다.");
    }

    @DisplayName("경로에 기물이 0개이거나 2개 이상이면 예외가 발생한다.")
    @Test
    void canMove_InvalidJumpCount_ThrowsException() {
        Cannon cannon = new Cannon(Team.HAN);
        Piece targetPiece = new EmptyPiece();

        assertAll(
                () -> assertThatThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of(new EmptyPiece(), new EmptyPiece())), targetPiece))
                        .hasMessage("[ERROR] 포는 오직 1개의 기물만 뛰어넘고 이동할 수 있습니다."),
                () -> assertThatThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of()), targetPiece))
                        .hasMessage("[ERROR] 포는 오직 1개의 기물만 뛰어넘고 이동할 수 있습니다."),
                () -> assertThatThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of(new Soldier(Team.HAN), new Elephant(Team.HAN))), targetPiece))
                        .hasMessage("[ERROR] 포는 오직 1개의 기물만 뛰어넘고 이동할 수 있습니다.")
        );
    }

    @DisplayName("궁성 대각선 1칸 이동은 넘을 기물이 없어 예외가 발생한다.")
    @Test
    void canMove_PalaceDiagonalOneStep_ThrowsException() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of()), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물만 뛰어넘고 이동할 수 있습니다.");
    }

    @DisplayName("궁성 대각선 2칸 이동 시 중심에 포가 아닌 기물이 있으면 정상적으로 이동 가능하다.")
    @Test
    void canMove_PalaceDiagonalTwoStep_ValidJump_DoesNotThrow() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatNoException()
                .isThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of(new Soldier(Team.CHO))), new EmptyPiece()));
    }

    @DisplayName("궁성 대각선 2칸 이동 시 중심에 포가 있으면 예외가 발생한다.")
    @Test
    void canMove_PalaceDiagonalTwoStep_JumpOverCannon_ThrowsException() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of(new Cannon(Team.CHO))), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
    }

    @DisplayName("포가 이동 경로에서 포를 뛰어넘으려 하면 예외가 발생한다.")
    @Test
    void canMove_JumpOverCannon_ThrowsException() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of(new EmptyPiece(), new Cannon(Team.HAN))), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
    }

    @DisplayName("포가 도착할 위치에 포가 있으면 예외가 발생한다.")
    @Test
    void canMove_TargetIsCannon_ThrowsException() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of(new Guard(Team.HAN))), new Cannon(Team.CHO)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 잡을 수 없습니다.");
    }

    @DisplayName("도착 위치에 아군 기물이 있으면 예외가 발생한다.")
    @Test
    void canMove_TargetIsSameTeam_ThrowsException() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of(new Guard(Team.HAN))), new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("포가 아닌 기물 1개를 넘고 도착 위치가 빈 칸이나 적군이면 정상적으로 이동 가능하다.")
    @Test
    void canMove_ValidJumpAndTarget_DoesNotThrow() {
        Cannon cannon = new Cannon(Team.HAN);

        assertThatNoException()
                .isThrownBy(() -> cannon.canMove(new PiecesOnPath(List.of(new EmptyPiece(), new Soldier(Team.HAN))), new Chariot(Team.CHO)));
    }
}
