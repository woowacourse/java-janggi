package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SoldierTest {
    private Soldier soldier;

    @BeforeEach
    void setUp() {
        soldier = new Soldier(TeamName.CHO, new Position(0, 0));
    }

    @DisplayName("정상: 졸병은 전진 방향으로 1칸을 이동 가능")
    @Test
    void validateSoldier() {
        assertThatCode(() -> soldier.validateMovement(new Position(0, 3), new Position(0, 4),
                PalaceArea.OUTSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("정상: 졸병은 궁성의 대각선을 타고 1칸을 이동 가능")
    @Test
    void validateSoldier_Palace() {
        assertThatCode(() -> soldier.validateMovement(new Position(4, 7), new Position(5, 8),
                PalaceArea.INSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("예외: 졸병은 후진 방향으로 이동 불가")
    @Test
    void validateSoldier_LimitY() {
        assertThatThrownBy(() -> soldier.validateMovement(new Position(0, 3), new Position(0, 2),
                PalaceArea.OUTSIDE)).isInstanceOf(IllegalArgumentException.class);
    }
}
