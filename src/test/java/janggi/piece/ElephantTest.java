package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElephantTest {
    private Elephant elephant;

    @BeforeEach
    void setUp() {
        elephant = new Elephant(TeamName.CHO, new Position(0, 0));
    }

    @DisplayName("정상: 상은 전후좌우 방향으로 칸을 이동 가능")
    @Test
    void validateElephant() {
        assertThatCode(() -> elephant.validateMovement(new Position(1, 0), new Position(3, 3),
                PalaceArea.OUTSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("예외: 상은 전후좌우 방향으로 규칙대로 이동 가능")
    @Test
    void validateElephant_Rule() {
        assertThatThrownBy(() -> elephant.validateMovement(new Position(1, 0), new Position(3, 4),
                PalaceArea.OUTSIDE)).isInstanceOf(IllegalArgumentException.class);
    }
}
