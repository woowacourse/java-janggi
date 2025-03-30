package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseTest {
    private Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse(TeamName.CHO, new Position(0, 0));
    }

    @DisplayName("정상: 마는 전후좌우 방향으로 칸을 이동 가능")
    @Test
    void validateHorse() {
        assertThatCode(() -> horse.validateMovement(new Position(2, 0), new Position(3, 2),
                PalaceArea.OUTSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("예외: 마는 전후좌우 방향으로 규칙대로 이동 가능")
    @Test
    void validateHorse_Rule() {
        assertThatThrownBy(() -> horse.validateMovement(new Position(2, 0), new Position(3, 3),
                PalaceArea.OUTSIDE)).isInstanceOf(IllegalArgumentException.class);
    }
}
