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

@DisplayName("상(Elephant) 기물 테스트")
class ElephantTest {

    @DisplayName("같은 팀의 기물인지 확인하면 올바른 결과를 반환한다.")
    @Test
    void isSameTeam() {
        Elephant hanElephant = new Elephant(Team.HAN);
        Elephant sameTeamElephant = new Elephant(Team.HAN);
        Elephant diffTeamElephant = new Elephant(Team.CHO);

        assertAll(
                () -> assertThat(hanElephant.isSameTeam(sameTeamElephant)).isTrue(),
                () -> assertThat(hanElephant.isSameTeam(diffTeamElephant)).isFalse()
        );
    }

    @DisplayName("직선 1칸 후 대각선 2칸(상밭)으로 이동시키면 통과하는 경로(멱) 2개를 반환한다.")
    @Test
    void getPath_ValidMove() {
        Elephant elephant = new Elephant(Team.HAN);

        List<Position> path = elephant.getPath(Position.from("13"), Position.from("45"));

        assertThat(path).containsExactly(Position.from("23"), Position.from("34"));
    }

    @DisplayName("상의 고유한 경로(상밭)가 아닌 곳으로 이동시키려 하면 예외가 발생한다.")
    @Test
    void getPath_InvalidMove_ThrowsException() {
        Elephant elephant = new Elephant(Team.HAN);

        assertAll(
                () -> assertThatThrownBy(() -> elephant.getPath(Position.from("35"), Position.from("65")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 상은 해당 경로로 이동할 수 없습니다."),
                () -> assertThatThrownBy(() -> elephant.getPath(Position.from("11"), Position.from("33")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 상은 해당 경로로 이동할 수 없습니다.")
        );
    }

    @DisplayName("상의 이동 경로(멱)에 하나라도 기물이 존재하면 예외가 발생한다.")
    @Test
    void canMove_PathBlocked_ThrowsException() {
        Elephant elephant = new Elephant(Team.HAN);

        assertThatThrownBy(() -> elephant.canMove(new PiecesOnPath(List.of(new EmptyPiece(), new Soldier(Team.HAN))), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @DisplayName("도착 위치에 아군 기물이 있으면 이동할 수 없고 예외가 발생한다.")
    @Test
    void canMove_TargetIsSameTeam_ThrowsException() {
        Elephant elephant = new Elephant(Team.HAN);

        assertThatThrownBy(() -> elephant.canMove(new PiecesOnPath(List.of(new EmptyPiece(), new EmptyPiece())), new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("경로가 뚫려 있고 도착 위치에 적군 기물이 있으면 정상적으로 이동 가능하다.")
    @Test
    void canMove_ValidPathAndTarget_DoesNotThrow() {
        Elephant elephant = new Elephant(Team.HAN);

        assertThatNoException()
                .isThrownBy(() -> elephant.canMove(new PiecesOnPath(List.of(new EmptyPiece(), new EmptyPiece())), new Chariot(Team.CHO)));
    }
}
