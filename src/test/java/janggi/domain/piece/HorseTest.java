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

@DisplayName("마(Horse) 기물 테스트")
class HorseTest {

    @DisplayName("같은 팀의 기물인지 확인하면 올바른 결과를 반환한다.")
    @Test
    void isSameTeam() {
        Horse hanHorse = new Horse(Team.HAN);
        Horse sameTeamHorse = new Horse(Team.HAN);
        Horse diffTeamHorse = new Horse(Team.CHO);

        assertAll(
                () -> assertThat(hanHorse.isSameTeam(sameTeamHorse)).isTrue(),
                () -> assertThat(hanHorse.isSameTeam(diffTeamHorse)).isFalse()
        );
    }

    @DisplayName("직선 1칸 후 대각선 1칸(마밭)으로 이동시키면 통과하는 경로(멱)를 반환한다.")
    @Test
    void getPath_ValidMove() {
        Horse horse = new Horse(Team.HAN);

        List<Position> path = horse.getPath(Position.from("36"), Position.from("57"));

        assertThat(path).containsExactly(Position.from("46"));
    }

    @DisplayName("마의 고유한 경로(마밭)가 아닌 곳으로 이동시키려 하면 예외가 발생한다.")
    @Test
    void getPath_InvalidMove_ThrowsException() {
        Horse horse = new Horse(Team.HAN);

        assertAll(
                () -> assertThatThrownBy(() -> horse.getPath(Position.from("35"), Position.from("65")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 마는 해당 경로로 이동할 수 없습니다."),
                () -> assertThatThrownBy(() -> horse.getPath(Position.from("11"), Position.from("22")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 마는 해당 경로로 이동할 수 없습니다.")
        );
    }

    @DisplayName("마의 이동 경로(멱)에 기물이 있으면 예외가 발생한다.")
    @Test
    void canMove_PathBlocked_ThrowsException() {
        Horse horse = new Horse(Team.HAN);

        assertThatThrownBy(() -> horse.canMove(new PiecesOnPath(List.of(new Soldier(Team.HAN))), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 마의 이동 경로에 기물이 있을 수 없습니다.");
    }

    @DisplayName("도착 위치에 아군 기물이 있으면 이동할 수 없고 예외가 발생한다.")
    @Test
    void canMove_TargetIsSameTeam_ThrowsException() {
        Horse horse = new Horse(Team.HAN);

        assertThatThrownBy(() -> horse.canMove(new PiecesOnPath(List.of(new EmptyPiece())), new Soldier(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("경로가 뚫려 있고 도착 위치에 적군 기물이 있으면 정상적으로 이동 가능하다.")
    @Test
    void canMove_ValidPathAndTarget_DoesNotThrow() {
        Horse horse = new Horse(Team.HAN);

        assertThatNoException()
                .isThrownBy(() -> horse.canMove(new PiecesOnPath(List.of(new EmptyPiece())), new Chariot(Team.CHO)));
    }
}
