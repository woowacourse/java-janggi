package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GuardTest {
    private Guard guard;

    @BeforeEach
    void setUp() {
        guard = new Guard(TeamName.CHO, new Position(0, 0));
    }

    @DisplayName("정상: 사는 전후좌우로 1칸을 이동 가능")
    @Test
    void validateGuard() {
        assertThatCode(() -> guard.validateMovement(new Position(3, 0), new Position(3, 1),
                PalaceArea.INSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("정상: 사는 궁성의 대각선을 타고 1칸을 이동 가능")
    @Test
    void validateGuard_Palace() {
        assertThatCode(() -> guard.validateMovement(new Position(3, 0), new Position(4, 1),
                PalaceArea.INSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("예외: 사는 좌우 최대 1칸 이동 가능")
    @Test
    void validateGuard_LimitX() {
        assertThatThrownBy(() -> guard.validateMovement(new Position(3, 0), new Position(5, 0),
                PalaceArea.INSIDE)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예외: 사는 전후 최대 1칸 이동 가능")
    @Test
    void validateGuard_LimitY() {
        assertThatThrownBy(() -> guard.validateMovement(new Position(3, 0), new Position(3, 3),
                PalaceArea.INSIDE)).isInstanceOf(IllegalArgumentException.class);
    }
}
